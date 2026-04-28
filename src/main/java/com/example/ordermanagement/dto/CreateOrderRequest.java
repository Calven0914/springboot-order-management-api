package com.example.ordermanagement.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateOrderRequest {

    @NotNull
    private Long customerId;

    @Valid
    @NotEmpty
    private List<OrderLineRequest> items;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderLineRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderLineRequest> items) {
        this.items = items;
    }

    public static class OrderLineRequest {

        @NotNull
        private Long productId;

        @NotNull
        @Min(1)
        private Integer quantity;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}

