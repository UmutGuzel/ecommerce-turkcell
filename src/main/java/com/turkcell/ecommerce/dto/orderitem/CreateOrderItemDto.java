package com.turkcell.ecommerce.dto.orderitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateOrderItemDto {

    @NotBlank
    private String status;

    @NotBlank
    @PositiveOrZero
    private BigDecimal price;

    @NotBlank
    @PositiveOrZero
    private Integer quantity;

    private UUID orderId;
    private UUID productId;

    public @NotBlank String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank String status) {
        this.status = status;
    }

    public @NotBlank @PositiveOrZero BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotBlank @PositiveOrZero BigDecimal price) {
        this.price = price;
    }

    public @NotBlank @PositiveOrZero Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotBlank @PositiveOrZero Integer quantity) {
        this.quantity = quantity;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
