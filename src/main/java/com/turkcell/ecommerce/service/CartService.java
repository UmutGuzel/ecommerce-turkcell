package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.cart.CartDto;

import java.util.List;
import java.util.UUID;

public interface CartService {
    CartDto addProductToCart(UUID cartId, UUID productId, Integer quantity);

    CartDto getCart(UUID userId);

    CartDto createCart(UUID userId);

    CartDto updateProductQuantityInCart(UUID cartId, UUID productId, Integer quantity);

    String deleteProductFromCart(UUID cartId, UUID productId);
}
