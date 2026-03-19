package com.daniel.ordermanagement.repository;

import com.daniel.ordermanagement.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para gestionar los pedidos (CustomerOrder).
 *
 * Al extender JpaRepository, permite realizar operaciones básicas
 * como guardar, buscar, actualizar y eliminar pedidos en la base de datos.
 */
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}