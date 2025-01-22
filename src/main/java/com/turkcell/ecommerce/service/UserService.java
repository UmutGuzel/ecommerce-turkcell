package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.dto.user.UpdateUserDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void add(CreateUserDto createUserDto);
    void update(UpdateUserDto updateUserDto);
    List<ListUserDto> getAll();
    Optional<User> findById(UUID id);

}
