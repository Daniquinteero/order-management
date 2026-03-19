package com.daniel.ordermanagement.dto;

import java.math.BigDecimal;

/**
 * DTO de respuesta que representa la información de un producto.
 */
public class ProductResponseDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;

    public ProductResponseDto() {
    }

    public ProductResponseDto(Long id, String name, String description, BigDecimal price, Integer stock, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
