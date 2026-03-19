package com.daniel.ordermanagement.exception;

/**
 * Excepción lanzada cuando un recurso solicitado no existe.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}