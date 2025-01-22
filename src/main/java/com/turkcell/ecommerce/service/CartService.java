package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.cart.CartListingDto;

import java.util.UUID;

public interface CartService {
    void addProductToCart(UUID userId, UUID productId, int quantity);
    void removeProductFromCart(UUID userId, UUID productId);
    CartListingDto getCartItems(UUID userId);
    void updateProductQuantity(UUID userId, UUID productId, int quantity);
}
