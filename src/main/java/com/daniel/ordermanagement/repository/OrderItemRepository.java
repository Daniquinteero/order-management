package com.daniel.ordermanagement.repository;

import com.daniel.ordermanagement.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}