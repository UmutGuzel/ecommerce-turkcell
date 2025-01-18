package com.turkcell.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_statuses")
public class OrderStatus {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(name="status")
    private String status;

    @OneToMany(mappedBy = "orderStatus", cascade = CascadeType.ALL)
    private List<Order> orders;
}
