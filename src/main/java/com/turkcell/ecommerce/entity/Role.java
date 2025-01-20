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
@Table(name = "roles")
public class Role {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

}
