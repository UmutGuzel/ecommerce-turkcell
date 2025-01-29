package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Cart findByUserId(UUID userId);

    Optional<Cart> findByIdAndUserEmail(UUID cartId, String email);
}
