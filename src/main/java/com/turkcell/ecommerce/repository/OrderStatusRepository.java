package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, UUID> {
}
