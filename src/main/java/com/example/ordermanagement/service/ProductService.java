package com.example.ordermanagement.service;

import com.example.ordermanagement.dto.CreateProductRequest;
import com.example.ordermanagement.dto.ProductResponse;
import com.example.ordermanagement.entity.Product;
import com.example.ordermanagement.exception.BusinessException;
import com.example.ordermanagement.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new BusinessException("Product SKU already exists");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        return toResponse(productRepository.save(product));
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}

