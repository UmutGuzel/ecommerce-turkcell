package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.role.CreateRoleDto;
import com.turkcell.ecommerce.dto.role.ListRoleDto;
import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
   RoleService roleService;
   public RoleController(RoleService roleService) {
      this.roleService = roleService;
   }


    @GetMapping("/all")
    public List<ListRoleDto> getAll() {
        return roleService.getAll();
    }

    @PostMapping
    public void add(@RequestBody @Valid CreateRoleDto createRoleDto) {
        roleService.add(createRoleDto);
    }

    @PutMapping
    public void update() {
        //TODO: implement
    }
}
