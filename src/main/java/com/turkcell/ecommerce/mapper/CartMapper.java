package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.entity.Cart;
import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CartMapper {

    List<OrderItem> toOrderItems(Cart cart, Order order);
}
