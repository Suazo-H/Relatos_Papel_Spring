package com.unir.relatos.orders.controller;

import com.unir.relatos.orders.controller.model.CreateOrderRequest;
import com.unir.relatos.orders.controller.model.OrderResponse;
import com.unir.relatos.orders.controller.model.OrdersListResponse;
import com.unir.relatos.orders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "API para gestión de pedidos de libros")
public class OrdersController {

    private final OrderService orderService;

    @Operation(
            summary = "Crear pedido",
            description = "Crea un nuevo pedido. Valida la disponibilidad de cada libro con el microservicio de catálogo antes de confirmar el pedido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado correctamente",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o libro no disponible"),
            @ApiResponse(responseCode = "503", description = "Servicio de catálogo no disponible")
    })
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pedido a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateOrderRequest.class)))
            @RequestBody CreateOrderRequest request) {
        OrderResponse order = orderService.createOrder(request);
        return ResponseEntity.status(201).body(order);
    }

    @Operation(
            summary = "Obtener pedidos de usuario",
            description = "Obtiene todos los pedidos de un usuario específico, ordenados por fecha de creación descendente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente",
                    content = @Content(schema = @Schema(implementation = OrdersListResponse.class)))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<OrdersListResponse> getOrdersByUser(
            @Parameter(description = "ID del usuario") @PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @Operation(
            summary = "Obtener pedido por ID",
            description = "Obtiene los detalles de un pedido específico por su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @Parameter(description = "ID del pedido") @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
}
