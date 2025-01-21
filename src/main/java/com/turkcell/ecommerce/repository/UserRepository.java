package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
