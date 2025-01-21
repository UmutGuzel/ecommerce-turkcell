//package com.turkcell.ecommerce.entity;
//
//import jakarta.persistence.*;
//import org.hibernate.annotations.UuidGenerator;
//
//import java.sql.Date;
//import java.util.UUID;
//
//@Entity
//@Table(name="user_roles")
//public class UserRole {
//
//    @Id
//    @UuidGenerator
//    private UUID id;
//
//    @Column(name="created_at")
//    private Date createdAt;
//    @Column(name="updated_at")
//    private Date updatedAt;
//
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name="role_id")
//    private Role role;
//
//}
