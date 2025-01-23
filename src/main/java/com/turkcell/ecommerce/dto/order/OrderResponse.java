package com.turkcell.ecommerce.dto.order;

import com.turkcell.ecommerce.dto.orderitem.OrderItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private UUID orderId;
    private String orderNumber;
    private List<OrderItemResponse> items;
    private String status;
    private Date orderDate;


}