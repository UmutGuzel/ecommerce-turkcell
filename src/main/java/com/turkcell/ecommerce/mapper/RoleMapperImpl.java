package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.permission.ListPermissionDto;
import com.turkcell.ecommerce.dto.role.CreateRoleDto;
import com.turkcell.ecommerce.dto.role.ListRoleDto;
import com.turkcell.ecommerce.dto.role.UpdateRoleDto;
import com.turkcell.ecommerce.entity.Permission;
import com.turkcell.ecommerce.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMapperImpl implements RoleMapper {
    @Override
    public Role toEntity(CreateRoleDto createRoleDto) {
        Role role = new Role();
        role.setName(createRoleDto.getName());
        return role;
    }

    @Override
    public Role toEntity(UpdateRoleDto updateRoleDto) {
        Role role = new Role();
        return role;
    }

    @Override
    public List<ListRoleDto> toListRoleDto(List<Role> roles) {
        return roles.stream()
                .map(role ->
                        new ListRoleDto(role.getName()))
                .toList();
    }
}
