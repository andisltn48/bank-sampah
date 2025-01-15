package com.babylo.banksampah.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babylo.banksampah.dto.UserDto;
import com.babylo.banksampah.dto.UserResponseDto;
import com.babylo.banksampah.responses.ApiResponse;
import com.babylo.banksampah.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse<UserResponseDto>> addUser(@Valid @RequestBody UserDto request) {
        ApiResponse<UserResponseDto> user = new ApiResponse<>(userService.addUser(request));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
}
