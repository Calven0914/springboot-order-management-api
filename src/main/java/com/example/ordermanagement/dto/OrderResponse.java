package com.example.ordermanagement.dto;

import com.example.ordermanagement.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private final Long id;
    private final Long customerId;
    private final String customerName;
    private final OrderStatus status;
    private final BigDecimal totalAmount;
    private final LocalDateTime createdAt;
    private final List<OrderItemResponse> items;

    public OrderResponse(
            Long id,
            Long customerId,
            String customerName,
            OrderStatus status,
            BigDecimal totalAmount,
            LocalDateTime createdAt,
            List<OrderItemResponse> items
    ) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public static class OrderItemResponse {

        private final Long productId;
        private final String productName;
        private final Integer quantity;
        private final BigDecimal unitPrice;
        private final BigDecimal lineTotal;

        public OrderItemResponse(
                Long productId,
                String productName,
                Integer quantity,
                BigDecimal unitPrice,
                BigDecimal lineTotal
        ) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.lineTotal = lineTotal;
        }

        public Long getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public BigDecimal getLineTotal() {
            return lineTotal;
        }
    }
}

