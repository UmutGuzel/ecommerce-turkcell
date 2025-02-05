package com.turkcell.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @UuidGenerator
    @Column(name="id")
    private UUID id;
    @Column(name="status")
    private String status;

    @Column(name="created_at")
    private Date createdAt;
    @Column(name="updated_at")
    private Date updatedAt;

    @Column(name="price")
    private BigDecimal price;
    @Column(name="quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

}
