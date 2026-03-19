package com.daniel.ordermanagement.service;

import com.daniel.ordermanagement.dto.CustomerRequestDto;
import com.daniel.ordermanagement.dto.CustomerResponseDto;
import com.daniel.ordermanagement.entity.Customer;
import com.daniel.ordermanagement.exception.BusinessException;
import com.daniel.ordermanagement.exception.ResourceNotFoundException;
import com.daniel.ordermanagement.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Gestiona la lógica de negocio de clientes:
 * - Creación de clientes con validación de email único
 * - Obtención de todos los clientes
 * - Búsqueda de clientes por ID
 * - Conversión de entidades a DTOs de respuesta
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDto createCustomer(CustomerRequestDto request) {

        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("El email ya está registrado");
        }

        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .createdAt(LocalDateTime.now())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CustomerResponseDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));

        return mapToResponse(customer);
    }

    private CustomerResponseDto mapToResponse(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt()
        );
    }
}