package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.dto.user.UpdateUserDto;
import com.turkcell.ecommerce.entity.User;
import com.turkcell.ecommerce.mapper.UserMapperImpl;
import com.turkcell.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapperImpl userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void add(CreateUserDto createUserDto) {
       User user = userMapper.toEntity(createUserDto);
       userRepository.save(user);
    }

    @Override
    public void update(UpdateUserDto updateUserDto) {

    }

    @Override
    public List<ListUserDto> getAll() {

        return List.of();
    }
}
