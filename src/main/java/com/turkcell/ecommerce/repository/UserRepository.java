package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
   Optional<User> findById(UUID id);


    Optional<User> findByEmail(String email);
}
