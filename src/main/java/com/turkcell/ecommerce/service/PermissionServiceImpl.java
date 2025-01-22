package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.permission.CreatePermissionDto;
import com.turkcell.ecommerce.entity.Permission;
import com.turkcell.ecommerce.mapper.PermissionMapper;
import com.turkcell.ecommerce.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public void add(CreatePermissionDto createPermissionDto) {
        permissionRepository.save(permissionMapper.toEntity(createPermissionDto));
    }

    @Override
    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    @Override
    public List<Permission> findByIds(List<UUID> ids) {
        return permissionRepository.findAllById(ids);
    }
}
