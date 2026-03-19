package com.daniel.ordermanagement.service;

import com.daniel.ordermanagement.dto.CustomerRequestDto;
import com.daniel.ordermanagement.dto.CustomerResponseDto;
import com.daniel.ordermanagement.entity.Customer;
import com.daniel.ordermanagement.exception.BusinessException;
import com.daniel.ordermanagement.exception.ResourceNotFoundException;
import com.daniel.ordermanagement.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldCreateCustomerSuccessfully() {
        CustomerRequestDto request = new CustomerRequestDto();
        request.setName("Daniel");
        request.setEmail("daniel@test.com");
        request.setPhone("111111111");

        when(customerRepository.findByEmail("daniel@test.com")).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);
            customer.setId(1L);
            customer.setCreatedAt(LocalDateTime.now());
            return customer;
        });

        CustomerResponseDto response = customerService.createCustomer(request);

        assertNotNull(response);
        assertEquals("Daniel", response.getName());
        assertEquals("daniel@test.com", response.getEmail());

        verify(customerRepository).findByEmail("daniel@test.com");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        CustomerRequestDto request = new CustomerRequestDto();
        request.setName("Daniel");
        request.setEmail("daniel@test.com");
        request.setPhone("111111111");

        when(customerRepository.findByEmail("daniel@test.com"))
                .thenReturn(Optional.of(new Customer()));

        assertThrows(BusinessException.class, () -> customerService.createCustomer(request));

        verify(customerRepository).findByEmail("daniel@test.com");
        verify(customerRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(99L));

        verify(customerRepository).findById(99L);
    }
}
