package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.role.CreateRoleDto;
import com.turkcell.ecommerce.dto.role.ListRoleDto;
import com.turkcell.ecommerce.dto.role.UpdateRoleDto;
import com.turkcell.ecommerce.entity.Permission;
import com.turkcell.ecommerce.entity.Role;
import com.turkcell.ecommerce.mapper.RoleMapper;
import com.turkcell.ecommerce.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
//    private final PermissionService permissionService;
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
//        this.permissionService = permissionService;
    }
    @Override
    public void add(CreateRoleDto createRoleDto) {
//        List<Permission> permissions = permissionService.getPermissionsByNames(createRoleDto.getPermissions());
        Role role = roleMapper.toEntity(createRoleDto);

        roleRepository.save(role);
    }

    @Override
    public Role add(String name) {
        Role role = roleMapper.toEntity(name);
        return roleRepository.save(role);
    }

    @Override
    public void update(UpdateRoleDto updateRoleDto) {

        //TODO: implement
    }

    @Override
    public List<ListRoleDto> getAll() {
        return roleMapper.toListRoleDto(roleRepository.findAll());
    }

    @Override
    public List<Role> getRolesByNames(List<String> names) {
        return roleRepository.findAllByNameIn(names);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }
}
