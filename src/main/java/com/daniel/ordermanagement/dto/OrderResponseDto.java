package com.daniel.ordermanagement.dto;

import com.daniel.ordermanagement.entity.OrderStatus;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta que representa un pedido con sus items asociados.
 */
@JacksonXmlRootElement(localName = "order")
public class OrderResponseDto {

    private Long id;
    private Long customerId;
    private String customerName;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;

    @JacksonXmlElementWrapper(localName = "items")
    private List<OrderItemResponseDto> items;

    public OrderResponseDto() {
    }

    public OrderResponseDto(Long id, Long customerId, String customerName, OrderStatus status,
                            LocalDateTime orderDate, BigDecimal totalAmount, List<OrderItemResponseDto> items) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItemResponseDto> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setItems(List<OrderItemResponseDto> items) {
        this.items = items;
    }
}