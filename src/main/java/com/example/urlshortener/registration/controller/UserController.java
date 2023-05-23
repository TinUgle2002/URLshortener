package com.example.urlshortener.registration.controller;

import com.example.urlshortener.registration.DTO.UserDTO;
import com.example.urlshortener.registration.DTO.UserResponseDTO;
import com.example.urlshortener.registration.service.UserService;
import com.example.urlshortener.registration.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/administration/registration")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }
}
