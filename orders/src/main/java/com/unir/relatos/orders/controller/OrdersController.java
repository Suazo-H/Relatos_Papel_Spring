package com.unir.relatos.orders.controller;

import com.unir.relatos.orders.controller.model.CreateOrderRequest;
import com.unir.relatos.orders.controller.model.OrderResponse;
import com.unir.relatos.orders.controller.model.OrdersListResponse;
import com.unir.relatos.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    /**
     * POST /api/v1/orders - Create a new order
     * Validates books with the catalogue microservice before creating the order.
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        OrderResponse order = orderService.createOrder(request);
        return ResponseEntity.status(201).body(order);
    }

    /**
     * GET /api/v1/orders/user/{userId} - Get orders for a specific user
     * Used for the user profile view to show recent orders.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<OrdersListResponse> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    /**
     * GET /api/v1/orders/{orderId} - Get a specific order by ID
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
}
