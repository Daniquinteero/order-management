package com.daniel.ordermanagement.service;

import com.daniel.ordermanagement.dto.AddOrderItemRequestDto;
import com.daniel.ordermanagement.dto.CreateOrderRequestDto;
import com.daniel.ordermanagement.dto.OrderResponseDto;
import com.daniel.ordermanagement.entity.*;
import com.daniel.ordermanagement.exception.BusinessException;
import com.daniel.ordermanagement.exception.ResourceNotFoundException;
import com.daniel.ordermanagement.repository.CustomerOrderRepository;
import com.daniel.ordermanagement.repository.CustomerRepository;
import com.daniel.ordermanagement.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CustomerOrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private Customer customer;
    private Product product;
    private CustomerOrder order;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1L)
                .name("Daniel")
                .email("daniel@test.com")
                .phone("123456789")
                .createdAt(LocalDateTime.now())
                .build();

        product = Product.builder()
                .id(1L)
                .name("Teclado")
                .description("Teclado mecánico")
                .price(new BigDecimal("50.00"))
                .stock(10)
                .active(true)
                .build();

        order = CustomerOrder.builder()
                .id(1L)
                .customer(customer)
                .status(OrderStatus.CREATED)
                .orderDate(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .build();
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        CreateOrderRequestDto request = new CreateOrderRequestDto();
        request.setCustomerId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(CustomerOrder.class))).thenAnswer(invocation -> {
            CustomerOrder savedOrder = invocation.getArgument(0);
            savedOrder.setId(1L);
            return savedOrder;
        });

        OrderResponseDto response = orderService.createOrder(request);

        assertNotNull(response);
        assertEquals(1L, response.getCustomerId());
        assertEquals("Daniel", response.getCustomerName());
        assertEquals(OrderStatus.CREATED, response.getStatus());
        assertEquals(BigDecimal.ZERO, response.getTotalAmount());

        verify(customerRepository).findById(1L);
        verify(orderRepository).save(any(CustomerOrder.class));
    }

    @Test
    void shouldThrowExceptionWhenCustomerDoesNotExist() {
        CreateOrderRequestDto request = new CreateOrderRequestDto();
        request.setCustomerId(99L);

        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(request));

        verify(customerRepository).findById(99L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldAddItemToOrderSuccessfully() {
        AddOrderItemRequestDto request = new AddOrderItemRequestDto();
        request.setProductId(1L);
        request.setQuantity(2);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(CustomerOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponseDto response = orderService.addItemToOrder(1L, request);

        assertNotNull(response);
        assertEquals(1, response.getItems().size());
        assertEquals(new BigDecimal("100.00"), response.getTotalAmount());
        assertEquals(8, product.getStock());

        verify(orderRepository).findById(1L);
        verify(productRepository).findById(1L);
        verify(orderRepository).save(any(CustomerOrder.class));
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        AddOrderItemRequestDto request = new AddOrderItemRequestDto();
        request.setProductId(1L);
        request.setQuantity(20);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(BusinessException.class, () -> orderService.addItemToOrder(1L, request));

        verify(orderRepository).findById(1L);
        verify(productRepository).findById(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductIsInactive() {
        product.setActive(false);

        AddOrderItemRequestDto request = new AddOrderItemRequestDto();
        request.setProductId(1L);
        request.setQuantity(1);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(BusinessException.class, () -> orderService.addItemToOrder(1L, request));

        verify(orderRepository).findById(1L);
        verify(productRepository).findById(1L);
        verify(orderRepository, never()).save(any());
    }
}