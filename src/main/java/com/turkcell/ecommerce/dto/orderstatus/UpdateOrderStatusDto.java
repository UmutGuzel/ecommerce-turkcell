package com.turkcell.ecommerce.dto.orderstatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateOrderStatusDto {

    @NotNull(message = "Id alanı boş bırakılamaz.")
    private UUID id;
    @NotBlank(message = "Status alanı boş bırakılamaz.")
    private String status;


}
