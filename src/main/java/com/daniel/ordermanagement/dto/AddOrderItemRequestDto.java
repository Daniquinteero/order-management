package com.daniel.ordermanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


/**
 * DTO used to add a product to an existing order.
 *
 * Contains the product ID and the quantity to be added.
 */
public class AddOrderItemRequestDto {

    @NotNull(message = "El productId es obligatorio")
    private Long productId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}