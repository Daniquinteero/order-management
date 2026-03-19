package com.daniel.ordermanagement.repository;

import com.daniel.ordermanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para gestionar los clientes.
 *
 * Permite realizar operaciones básicas sobre Customer y
 * buscar clientes por su email.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}