package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.Cart;
import com.turkcell.ecommerce.entity.CartItem;
import com.turkcell.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    CartItem findByCartAndProduct(Cart cart, Product product);
    List<CartItem> findByCart(Cart cart);
}
