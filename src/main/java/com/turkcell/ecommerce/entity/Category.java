package com.turkcell.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.util.*;

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

    @JsonBackReference
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="parentCategory")
    private Category parentCategory;



    @OneToMany(mappedBy="parentCategory")
    private List<Category> linkedCategory;

}
