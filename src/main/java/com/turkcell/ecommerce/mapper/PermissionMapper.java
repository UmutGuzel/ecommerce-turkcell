package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.permission.CreatePermissionDto;
import com.turkcell.ecommerce.dto.permission.ListPermissionDto;
import com.turkcell.ecommerce.entity.Permission;

import java.util.List;

public interface PermissionMapper {
    Permission toEntity(CreatePermissionDto createPermissionDto);
    List<ListPermissionDto> toListPermissionDto(List<Permission> permissions);
}
