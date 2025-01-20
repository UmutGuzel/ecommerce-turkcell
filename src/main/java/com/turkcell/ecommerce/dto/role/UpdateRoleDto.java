package com.turkcell.ecommerce.dto.role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateRoleDto {
    private String name;
    private List<UUID> permissionIds;
}
