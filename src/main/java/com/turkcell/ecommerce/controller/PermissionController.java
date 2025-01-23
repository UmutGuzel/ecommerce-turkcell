package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.permission.CreatePermissionDto;
import com.turkcell.ecommerce.entity.Permission;
import com.turkcell.ecommerce.service.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {
    PermissionService permissionService;
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/all")
    public List<Permission> getAll() {
        return permissionService.getAll();
    }

    @PostMapping
    public void add(CreatePermissionDto createPermissionDto) {
        permissionService.add(createPermissionDto);
    }

}
