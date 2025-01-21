package com.turkcell.ecommerce.dto.orderstatus;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderStatusDto {

    private UUID id;
    private String status;
}
