package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.role.CreateRoleDto;
import com.turkcell.ecommerce.dto.role.ListRoleDto;
import com.turkcell.ecommerce.dto.role.UpdateRoleDto;
import com.turkcell.ecommerce.entity.Permission;
import com.turkcell.ecommerce.entity.Role;

import java.util.List;

public interface RoleMapper {
    Role toEntity(CreateRoleDto createRoleDto);
    Role toEntity(UpdateRoleDto updateRoleDto);
    List<ListRoleDto> toListRoleDto(List<Role> roles);
}
