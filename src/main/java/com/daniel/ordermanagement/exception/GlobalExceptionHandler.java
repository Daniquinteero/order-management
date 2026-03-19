package com.daniel.ordermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la API.
 *
 * Centraliza el manejo de errores y devuelve respuestas HTTP consistentes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja cuando un recurso no es encontrado.
     * Devuelve 404 NOT FOUND.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Maneja errores de lógica de negocio.
     * Devuelve 400 BAD REQUEST.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Maneja errores de validación de DTOs (Bean Validation).
     * Devuelve un mapa campo -> mensaje de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Maneja cualquier excepción no controlada.
     * Devuelve 500 INTERNAL SERVER ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno");
    }

    /**
     * Construye una respuesta estándar de error con timestamp, status y mensaje.
     */
    private ResponseEntity<?> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}