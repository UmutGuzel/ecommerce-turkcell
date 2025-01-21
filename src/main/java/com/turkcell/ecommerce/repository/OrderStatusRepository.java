package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, UUID> {

    Optional<OrderStatus> findByStatus(String name);

    Optional<OrderStatus> findById(UUID id);
}
