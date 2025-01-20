package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<CartItem, UUID> {
}
