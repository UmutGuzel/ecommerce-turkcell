package com.turkcell.ecommerce.dto.permission;

import com.turkcell.ecommerce.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ListPermissionDto {
    String name;
    String description;
}
