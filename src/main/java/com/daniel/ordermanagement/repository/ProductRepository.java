package com.daniel.ordermanagement.repository;

import com.daniel.ordermanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para gestionar los productos.
 *
 * Permite realizar operaciones básicas sobre Product
 * como guardar, buscar, actualizar y eliminar.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}