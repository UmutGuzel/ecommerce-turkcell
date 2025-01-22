package com.turkcell.ecommerce.dto.order;


import com.turkcell.ecommerce.dto.orderitem.OrderItemListiningDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListingDto {

    private UUID id;
    private String status;

    private List<OrderItemListiningDto> orderItems;
}
