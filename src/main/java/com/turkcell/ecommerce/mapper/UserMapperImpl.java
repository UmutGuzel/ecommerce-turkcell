package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.UpdateUserDto;
import com.turkcell.ecommerce.entity.User;
import org.springframework.stereotype.Service;


@Service
public class UserMapperImpl implements UserMapper {
    public User toEntity(CreateUserDto createUserDto){
        User user = new User();
        user.setName(createUserDto.getName());
        user.setSurname(createUserDto.getSurname());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());
        return user;
    }
    public User toEntity(UpdateUserDto updateUserDto){
        User user = new User();
        user.setName(updateUserDto.getName());
        user.setSurname(updateUserDto.getSurname());
        user.setEmail(updateUserDto.getEmail());
        user.setPassword(updateUserDto.getPassword());
        return user;
    }
}
