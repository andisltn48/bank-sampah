package com.babylo.banksampah.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babylo.banksampah.dto.UserDto;
import com.babylo.banksampah.dto.UserResponseDto;
import com.babylo.banksampah.entities.User;
import com.babylo.banksampah.responses.ApiResponse;
import com.babylo.banksampah.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ApiResponse<UserResponseDto> postMethodName(@RequestBody UserDto request) {
        UserResponseDto user = userService.addUser(request);
        return new ApiResponse<>("success", "User added successfully", user);
    }
    
}
