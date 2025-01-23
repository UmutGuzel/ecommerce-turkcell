package com.turkcell.ecommerce.dto.orderstatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusDto {

    @NotNull(message = "Id alanı boş bırakılamaz.")
    private UUID orderId;
    @NotBlank(message = "Status alanı boş bırakılamaz.")
    private String newStatus;



}
