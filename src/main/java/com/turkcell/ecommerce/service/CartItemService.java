package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.cart.CreateCartDto;
import com.turkcell.ecommerce.dto.cart.UpdateCartDto;

public interface CartItemService {
    void add(CreateCartDto createCartDto);
    void update(UpdateCartDto updateCartDto);
}
