package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);

    List<Role> findAllByNameIn(List<String> names);
    Role getRoleByName(String name);
}
