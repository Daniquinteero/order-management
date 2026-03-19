package com.daniel.ordermanagement.exception;

/**
 * Excepción para representar errores de lógica de negocio.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}