package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.cart.AddProductToCartDto;
import com.turkcell.ecommerce.dto.cart.CartDto;
import com.turkcell.ecommerce.dto.cart.DeleteProductFromCartDto;
import com.turkcell.ecommerce.dto.cart.UpdateCartProductQuantityDto;
import com.turkcell.ecommerce.entity.User;

import java.util.UUID;

public interface CartService {
    void addProductToCart(AddProductToCartDto addProductToCartDto);

    CartDto getCart(UUID userId);

    void createCart(User user);

    void updateCartProductQuantity(UpdateCartProductQuantityDto updateCartProductQuantityDto);

    void deleteProductFromCart(DeleteProductFromCartDto deleteProductFromCartDto);
}
