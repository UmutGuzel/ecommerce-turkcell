package com.turkcell.ecommerce.dto.orderstatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateOrderStatusDto {

    @NotBlank(message = "Status alanı boş bırakılamaz.")
    private String status;



}
