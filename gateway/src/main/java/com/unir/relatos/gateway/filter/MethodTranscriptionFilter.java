package com.unir.relatos.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Global filter that transcribes POST requests to other HTTP methods
 * based on the X-HTTP-Method-Override header.
 * 
 * This allows the frontend to send all requests as POST and have the
 * gateway convert them to the appropriate method (GET, PUT, PATCH, DELETE).
 */
@Component
@Slf4j
public class MethodTranscriptionFilter implements GlobalFilter, Ordered {

    private static final String METHOD_OVERRIDE_HEADER = "X-HTTP-Method-Override";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        // Only process POST requests with the override header
        if (HttpMethod.POST.equals(request.getMethod())) {
            String methodOverride = request.getHeaders().getFirst(METHOD_OVERRIDE_HEADER);
            
            if (methodOverride != null && !methodOverride.isBlank()) {
                HttpMethod targetMethod = HttpMethod.valueOf(methodOverride.toUpperCase());
                
                log.info("Transcribing POST request to {} for path: {}", 
                        targetMethod, request.getPath());
                
                // Create a new request with the overridden method
                ServerHttpRequest mutatedRequest = request.mutate()
                        .method(targetMethod)
                        .build();
                
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            }
        }
        
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // Run early in the filter chain
        return -100;
    }
}
