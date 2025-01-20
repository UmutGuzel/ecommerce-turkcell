package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.role.CreateRoleDto;
import com.turkcell.ecommerce.dto.role.ListRoleDto;
import com.turkcell.ecommerce.dto.role.UpdateRoleDto;

import java.util.List;

public interface RoleService {
    void add(CreateRoleDto createRoleDto);
    void update(UpdateRoleDto updateRoleDto);
    List<ListRoleDto> getAll();
}
