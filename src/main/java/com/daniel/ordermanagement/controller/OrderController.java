package com.daniel.ordermanagement.controller;

import com.daniel.ordermanagement.dto.AddOrderItemRequestDto;
import com.daniel.ordermanagement.dto.CreateOrderRequestDto;
import com.daniel.ordermanagement.dto.OrderResponseDto;
import com.daniel.ordermanagement.dto.UpdateOrderStatusRequestDto;
import com.daniel.ordermanagement.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing orders.
 *
 * Provides endpoints for:
 * - Creating orders
 * - Adding items to orders
 * - Updating order status
 * - Retrieving orders
 * - Exporting orders in JSON, XML, and CSV formats
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@Valid @RequestBody CreateOrderRequestDto request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/{orderId}/items")
    public OrderResponseDto addItemToOrder(@PathVariable Long orderId,
                                           @Valid @RequestBody AddOrderItemRequestDto request) {
        return orderService.addItemToOrder(orderId, request);
    }

    @GetMapping
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PatchMapping("/{id}/status")
    public OrderResponseDto updateOrderStatus(@PathVariable Long id,
                                              @Valid @RequestBody UpdateOrderStatusRequestDto request) {
        return orderService.updateOrderStatus(id, request);
    }

    @GetMapping(value = "/{id}/export/xml", produces = "application/xml")
    public OrderResponseDto exportOrderXml(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping(value = "/{id}/export", produces = "application/json")
    public OrderResponseDto exportOrderJson(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

}