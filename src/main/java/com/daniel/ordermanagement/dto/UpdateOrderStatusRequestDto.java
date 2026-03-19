package com.daniel.ordermanagement.dto;

import com.daniel.ordermanagement.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para actualizar el estado de un pedido.
 */
public class UpdateOrderStatusRequestDto {

    @NotNull(message = "El estado es obligatorio")
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}