package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.entity.Cart;
import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.entity.OrderItem;
import com.turkcell.ecommerce.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartMapperImpl implements CartMapper {

    public List<OrderItem> toOrderItems(Cart cart, Order order) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();


            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());
    }


}
