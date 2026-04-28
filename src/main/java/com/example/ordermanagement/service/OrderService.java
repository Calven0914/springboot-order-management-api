package com.example.ordermanagement.service;

import com.example.ordermanagement.dto.CreateOrderRequest;
import com.example.ordermanagement.dto.OrderResponse;
import com.example.ordermanagement.dto.UpdateOrderStatusRequest;
import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.entity.OrderItem;
import com.example.ordermanagement.entity.OrderStatus;
import com.example.ordermanagement.entity.Product;
import com.example.ordermanagement.entity.PurchaseOrder;
import com.example.ordermanagement.exception.BusinessException;
import com.example.ordermanagement.exception.ResourceNotFoundException;
import com.example.ordermanagement.repository.CustomerRepository;
import com.example.ordermanagement.repository.ProductRepository;
import com.example.ordermanagement.repository.PurchaseOrderRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final PurchaseOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(
            PurchaseOrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + request.getCustomerId()));

        PurchaseOrder order = new PurchaseOrder();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.CREATED);

        BigDecimal total = BigDecimal.ZERO;
        for (CreateOrderRequest.OrderLineRequest line : request.getItems()) {
            Product product = productRepository.findById(line.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + line.getProductId()));

            if (product.getStockQuantity() < line.getQuantity()) {
                throw new BusinessException("Not enough stock for product: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - line.getQuantity());

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(line.getQuantity()));
            total = total.add(lineTotal);

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(line.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setLineTotal(lineTotal);
            order.addItem(item);
        }

        order.setTotalAmount(total);
        PurchaseOrder savedOrder = orderRepository.save(order);
        return toResponse(savedOrder);
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrder(Long id) {
        PurchaseOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        return toResponse(order);
    }

    @Transactional
    public OrderResponse updateStatus(Long id, UpdateOrderStatusRequest request) {
        PurchaseOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(request.getStatus());
        return toResponse(order);
    }

    private OrderResponse toResponse(PurchaseOrder order) {
        List<OrderResponse.OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> new OrderResponse.OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getLineTotal()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                items
        );
    }
}
