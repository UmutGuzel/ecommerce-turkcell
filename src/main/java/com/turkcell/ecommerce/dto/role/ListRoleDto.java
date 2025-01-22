package com.turkcell.ecommerce.dto.role;

import com.turkcell.ecommerce.dto.permission.ListPermissionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ListRoleDto {
    private String name;
    private List<ListPermissionDto> permissions;
}
