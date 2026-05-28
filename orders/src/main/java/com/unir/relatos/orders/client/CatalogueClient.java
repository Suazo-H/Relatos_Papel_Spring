package com.unir.relatos.orders.client;

import com.unir.relatos.orders.client.model.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for communication with the Catalogue microservice.
 * Uses the service name "catalogue" registered in Eureka (no IP/port needed).
 * Eureka's client-side load balancer handles service discovery.
 */
@FeignClient(name = "catalogue")
public interface CatalogueClient {

    /**
     * Get a book by its ID from the catalogue service.
     * Used to validate that books exist and are available before creating an order.
     */
    @GetMapping("/api/v1/books/{bookId}")
    BookResponse getBook(@PathVariable("bookId") Integer bookId);
}
