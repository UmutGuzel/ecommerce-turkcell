package com.turkcell.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories",uniqueConstraints=@UniqueConstraint(columnNames = "name"))
public class Category {
    @Id
    @UuidGenerator
    @Column(name="id")
    private UUID id;
    @Column(name="name",unique = true)
    private String name;

    @Column(name="created_at")
    private Date createdAt;
    @Column(name="updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;


    //alt kategori
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;


    //üst kategori
    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Category> subCategories=new ArrayList<>();

}
