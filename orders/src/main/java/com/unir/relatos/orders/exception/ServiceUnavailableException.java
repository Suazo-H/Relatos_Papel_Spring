package com.unir.relatos.orders.exception;

/**
 * Exception thrown when an upstream service (like Catalogue) is unavailable.
 */
public class ServiceUnavailableException extends RuntimeException {
    
    public ServiceUnavailableException(String message) {
        super(message);
    }
    
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
