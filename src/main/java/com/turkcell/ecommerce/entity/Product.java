package com.turkcell.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @UuidGenerator
    @Column(name="id")
    private UUID id;
    @Column(name="name", unique = true)
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="price")
    private BigDecimal price;
    @Column(name="stock")
    private Integer stock;
    @Column(name="image")
    private String image;

    @Column(name="created_at")
    private Date createdAt;
    @Column(name="updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> orderDetails;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderItem> orderItems;
}
