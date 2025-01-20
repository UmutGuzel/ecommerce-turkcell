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
@Table(name = "permissions")
public class Permission {
    @Id
    @UuidGenerator
    @Column(name="id")
    private UUID id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;
}
