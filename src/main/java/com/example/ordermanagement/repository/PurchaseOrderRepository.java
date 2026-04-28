package com.example.ordermanagement.repository;

import com.example.ordermanagement.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Override
    @EntityGraph(attributePaths = {"customer", "items", "items.product"})
    java.util.List<PurchaseOrder> findAll();

    @Override
    @EntityGraph(attributePaths = {"customer", "items", "items.product"})
    java.util.Optional<PurchaseOrder> findById(Long id);
}
