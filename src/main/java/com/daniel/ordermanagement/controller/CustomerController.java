package com.daniel.ordermanagement.controller;

import com.daniel.ordermanagement.dto.CustomerRequestDto;
import com.daniel.ordermanagement.dto.CustomerResponseDto;
import com.daniel.ordermanagement.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing customers.
 *
 * Exposes endpoints for:
 * - Creating customers
 * - Retrieving customers
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto createCustomer(@Valid @RequestBody CustomerRequestDto request) {
        return customerService.createCustomer(request);
    }

    @GetMapping
    public List<CustomerResponseDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerResponseDto getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}