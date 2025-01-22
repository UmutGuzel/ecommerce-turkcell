package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.permission.CreatePermissionDto;
import com.turkcell.ecommerce.dto.permission.ListPermissionDto;
import com.turkcell.ecommerce.entity.Permission;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class PermissionMapperImpl implements PermissionMapper{

    @Override
    public Permission toEntity(CreatePermissionDto createPermissionDto) {
        Permission permission = new Permission();
        permission.setName(createPermissionDto.getName());
        permission.setDescription(createPermissionDto.getDescription());
        permission.setCreatedAt(new Date(System.currentTimeMillis()));
        permission.setUpdatedAt(new Date(System.currentTimeMillis()));
        return permission;
    }

    @Override
    public List<ListPermissionDto> toListPermissionDto(List<Permission> permissions) {
        return List.of();
    }
}
