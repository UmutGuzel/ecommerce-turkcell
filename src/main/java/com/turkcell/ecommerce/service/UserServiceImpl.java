package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.dto.user.ChangeUserPasswordDto;
import com.turkcell.ecommerce.dto.user.LoginUserDto;
import com.turkcell.ecommerce.entity.Role;
import com.turkcell.ecommerce.entity.User;
import com.turkcell.ecommerce.mapper.UserMapper;
import com.turkcell.ecommerce.repository.UserRepository;
import com.turkcell.ecommerce.rules.UserBusinessRules;
import com.turkcell.ecommerce.util.jwt.JwtService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final UserBusinessRules userBusinessRules;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtService jwtService, UserBusinessRules userBusinessRules, RoleService roleService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.userBusinessRules = userBusinessRules;
        this.roleService = roleService;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void add(CreateUserDto createUserDto) {
       Optional<User> optionalUser = userRepository.findByEmail(createUserDto.getEmail());
       userBusinessRules.ValidateUser(optionalUser);

       List<Role> roles = roleService.getRolesByNames(createUserDto.getRoles());
       if(!roles.isEmpty())
           userBusinessRules.ValidateRoles(roles);

       createUserDto.setPassword(bCryptPasswordEncoder.encode(createUserDto.getPassword()));
       User user = userMapper.toEntity(createUserDto, roles);

       userRepository.save(user);
    }

    @Override
    public void update(ChangeUserPasswordDto changeUserPasswordDto) {
        Optional<User> optionalUser = userRepository.findByEmail(changeUserPasswordDto.getEmail());
        userBusinessRules.ValidateUser(optionalUser, changeUserPasswordDto);
        User user = optionalUser.get();
        user.setPassword(bCryptPasswordEncoder.encode(changeUserPasswordDto.getNewPassword()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        userRepository.save(user);
    }

    @Override
    public List<ListUserDto> getAll() {
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        List<User> users = userRepository.findAll();
        return userMapper.toListUserDto(users);
    }

    @Override
    public String login(LoginUserDto loginUserDto) {
        Optional<User> optionalUser = userRepository.findByEmail(loginUserDto.getEmail());
        userBusinessRules.ValidateUser(optionalUser, loginUserDto);
        User user = optionalUser.get();
        Map<String,Object> roles = new HashMap<>();
        roles.put("roles", user.getRoles().stream().map(c->c.getName()).toList());
        return jwtService.generateToken(user.getEmail(), roles);

    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }
}
