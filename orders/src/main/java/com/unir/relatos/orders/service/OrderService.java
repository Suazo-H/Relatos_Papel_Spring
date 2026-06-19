package com.unir.relatos.orders.service;

import com.unir.relatos.orders.client.CatalogueClient;
import com.unir.relatos.orders.client.model.BookResponse;
import com.unir.relatos.orders.controller.model.CreateOrderRequest;
import com.unir.relatos.orders.controller.model.OrderResponse;
import com.unir.relatos.orders.controller.model.OrdersListResponse;
import com.unir.relatos.orders.exception.BookValidationException;
import com.unir.relatos.orders.exception.OrderNotFoundException;
import com.unir.relatos.orders.exception.ServiceUnavailableException;
import com.unir.relatos.orders.repository.OrderRepository;
import com.unir.relatos.orders.repository.model.Order;
import com.unir.relatos.orders.repository.model.OrderItem;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CatalogueClient catalogueClient;

    /**
     * Create a new order.
     * Validates each book with the catalogue microservice before creating the order.
     */
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        // Validate request
        if (request == null || request.getUserId() == null || request.getItems() == null || request.getItems().isEmpty()) {
            throw new BookValidationException("An order must include a user ID and at least one item");
        }
        
        log.info("Creating order for user: {}", request.getUserId());

        // Build the order
        Order order = Order.builder()
                .userId(request.getUserId())
                .userEmail(request.getUserEmail())
                .status("CONFIRMED")
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        // Validate each book and create order items
        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            // Validate item has valid bookId and positive quantity
            if (itemRequest.getBookId() == null || itemRequest.getQuantity() == null || itemRequest.getQuantity() <= 0) {
                throw new BookValidationException("Each order item must include a book ID and a positive quantity");
            }
            
            BookResponse book = validateBook(itemRequest.getBookId(), itemRequest.getQuantity());

            BigDecimal unitPrice = BigDecimal.valueOf(book.getPrice());
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .bookId(book.getId())
                    .bookTitle(book.getTitle())
                    .bookIsbn(book.getIsbn())
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(unitPrice)
                    .subtotal(subtotal)
                    .build();

            order.addItem(orderItem);
            totalAmount = totalAmount.add(subtotal);
        }

        order.setTotalAmount(totalAmount);

        // Save the order
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with ID: {}", savedOrder.getId());

        return mapToResponse(savedOrder);
    }

    /**
     * Get orders for a specific user (for profile view).
     */
    @Transactional(readOnly = true)
    public OrdersListResponse getOrdersByUser(Long userId) {
        log.info("Fetching orders for user: {}", userId);
        
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        
        List<OrderResponse> orderResponses = orders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return OrdersListResponse.builder()
                .orders(orderResponses)
                .totalOrders(orderResponses.size())
                .build();
    }

    /**
     * Get a specific order by ID.
     */
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        log.info("Fetching order: {}", orderId);
        
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        return mapToResponse(order);
    }

    /**
     * Validate a book exists, is visible, and has sufficient stock.
     * Communicates with the Catalogue microservice via Feign (using Eureka service discovery).
     */
    private BookResponse validateBook(Long bookId, Integer requestedQuantity) {
        try {
            log.debug("Validating book {} with catalogue service", bookId);
            BookResponse book = catalogueClient.getBook(bookId);

            // Check if book is visible
            if (book.getVisible() == null || !book.getVisible()) {
                throw new BookValidationException("Book with ID " + bookId + " is not available for purchase");
            }

            // Check stock
            if (book.getStock() == null || book.getStock() < requestedQuantity) {
                throw new BookValidationException(
                        "Insufficient stock for book '" + book.getTitle() + "'. " +
                        "Requested: " + requestedQuantity + ", Available: " + (book.getStock() != null ? book.getStock() : 0));
            }

            return book;

        } catch (FeignException.NotFound e) {
            throw new BookValidationException("Book with ID " + bookId + " not found in catalogue");
        } catch (FeignException e) {
            log.error("Error communicating with catalogue service: {}", e.getMessage());
            throw new ServiceUnavailableException("Unable to validate book. Catalogue service unavailable.");
        }
    }

    /**
     * Map Order entity to OrderResponse DTO.
     */
    private OrderResponse mapToResponse(Order order) {
        List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderResponse.OrderItemResponse.builder()
                        .id(item.getId())
                        .bookId(item.getBookId())
                        .bookTitle(item.getBookTitle())
                        .bookIsbn(item.getBookIsbn())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .userEmail(order.getUserEmail())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }
}
