package com.unir.relatos.orders.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request DTO for creating a new order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    
    private Long userId;
    private String userEmail;
    private List<OrderItemRequest> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemRequest {
        private Integer bookId;
        private Integer quantity;
    }
}
