package com.unir.relatos.orders.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO for a list of orders.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersListResponse {
    private List<OrderResponse> orders;
    private int totalOrders;
}
