package com.daniel.ordermanagement.service;

import com.daniel.ordermanagement.dto.*;
import com.daniel.ordermanagement.entity.*;
import com.daniel.ordermanagement.exception.BusinessException;
import com.daniel.ordermanagement.exception.ResourceNotFoundException;
import com.daniel.ordermanagement.repository.CustomerOrderRepository;
import com.daniel.ordermanagement.repository.CustomerRepository;
import com.daniel.ordermanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Gestiona la lógica de negocio de pedidos:
 * - Creación de pedidos asociados a un cliente
 * - Añadir productos a un pedido con validación de existencia, estado y stock
 * - Actualización del stock y recálculo del total del pedido
 * - Obtención de todos los pedidos o de un pedido por ID
 * - Actualización del estado del pedido según las reglas definidas
 * - Conversión de entidades a DTOs de respuesta
 */
@Service
public class OrderService {

    private final CustomerOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(CustomerOrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + request.getCustomerId()));

        CustomerOrder order = CustomerOrder.builder()
                .customer(customer)
                .status(OrderStatus.CREATED)
                .orderDate(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .build();

        CustomerOrder savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }


    /**
     * Adds a product to an existing order.
     *
     * Business rules:
     * - Product must exist
     * - Product must be active
     * - Stock must be sufficient
     * - Stock is reduced after adding
     * - Order total is recalculated
     */
    @Transactional
    public OrderResponseDto addItemToOrder(Long orderId, AddOrderItemRequestDto request) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + orderId));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + request.getProductId()));

        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new BusinessException("El producto está inactivo");
        }

        if (product.getStock() < request.getQuantity()) {
            throw new BusinessException("Stock insuficiente para el producto: " + product.getName());
        }

        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        OrderItem item = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .unitPrice(product.getPrice())
                .subtotal(subtotal)
                .build();

        order.getItems().add(item);

        product.setStock(product.getStock() - request.getQuantity());

        BigDecimal newTotal = order.getItems().stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(newTotal);

        CustomerOrder updatedOrder = orderRepository.save(order);
        return mapToResponse(updatedOrder);
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OrderResponseDto getOrderById(Long id) {
        CustomerOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));

        return mapToResponse(order);
    }

    private OrderResponseDto mapToResponse(CustomerOrder order) {
        List<OrderItemResponseDto> items = order.getItems().stream()
                .map(item -> new OrderItemResponseDto(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderResponseDto(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getStatus(),
                order.getOrderDate(),
                order.getTotalAmount(),
                items
        );
    }

    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto request) {

        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + orderId));

        OrderStatus currentStatus = order.getStatus();
        OrderStatus newStatus = request.getStatus();

        // Validación simple de flujo
        if (currentStatus == OrderStatus.CANCELLED) {
            throw new BusinessException("No se puede modificar un pedido cancelado");
        }

        if (currentStatus == OrderStatus.SHIPPED) {
            throw new BusinessException("El pedido ya ha sido enviado");
        }


        order.setStatus(newStatus);

        return mapToResponse(orderRepository.save(order));
    }

}
