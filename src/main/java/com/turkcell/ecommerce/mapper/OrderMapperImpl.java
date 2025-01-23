package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.order.OrderResponse;
import com.turkcell.ecommerce.dto.orderitem.OrderItemResponse;
import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.entity.OrderItem;
import com.turkcell.ecommerce.entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapperImpl implements OrderMapper {

    public OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream().map(this::toOrderItemResponse).collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                "ORD-" + order.getId().toString().substring(0, 8).toUpperCase(),
                itemResponses,
                order.getOrderStatus().getStatus(),
                order.getCreatedAt()
        );
    }

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        return new OrderItemResponse(
                product.getDescription(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
        );
    }
}
