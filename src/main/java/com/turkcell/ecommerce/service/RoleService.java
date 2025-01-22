package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.role.CreateRoleDto;
import com.turkcell.ecommerce.dto.role.ListRoleDto;
import com.turkcell.ecommerce.dto.role.UpdateRoleDto;
import com.turkcell.ecommerce.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    void add(CreateRoleDto createRoleDto);
    void update(UpdateRoleDto updateRoleDto);
    List<ListRoleDto> getAll();
    List<Role> getRolesByNames(List<String> names);
}
