package com.daniel.ordermanagement.dto;

import jakarta.validation.constraints.NotNull;


/**
 * DTO para crear un nuevo pedido asociado a un cliente.
 */
public class CreateOrderRequestDto {

    @NotNull(message = "El customerId es obligatorio")
    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}