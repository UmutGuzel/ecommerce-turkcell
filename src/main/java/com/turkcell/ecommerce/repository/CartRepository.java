package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.id = :cartId")
    Cart findCartByUserIdAndCartId(UUID userId, UUID cartId);
}
