package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Optional<Order> findById(UUID id);


}
