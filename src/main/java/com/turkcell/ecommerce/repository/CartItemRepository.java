package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.CartItem;
import com.turkcell.ecommerce.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    @Query("SELECT ci.product FROM CartItem ci WHERE ci.product.id = :productId")
    Product findProductById(UUID productId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    CartItem findCartItemByProductIdAndCartId(UUID cartId, UUID productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    void deleteCartItemByProductIdAndCartId(UUID cartId, UUID productId);

    boolean existsByCartIdAndProductId(UUID cartId, UUID productId);
}
