package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
   UserService userService;
   public UserController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("/all")
    public List<ListUserDto> getAll() {
        return userService.getAll();
    }

   @PostMapping
    public void add(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.add(createUserDto);
    }

    @PutMapping
    public void update() {
        userService.update(null);
        //TODO: implement
    }

}
