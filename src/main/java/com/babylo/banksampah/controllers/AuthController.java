package com.babylo.banksampah.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.babylo.banksampah.dto.LoginUserDto;
import com.babylo.banksampah.dto.TokenResponseDto;
import com.babylo.banksampah.dto.UserDto;
import com.babylo.banksampah.dto.UserResponseDto;
import com.babylo.banksampah.responses.ApiResponse;
import com.babylo.banksampah.services.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/api/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@Valid @RequestBody UserDto request) {
        UserResponseDto user = authService.register(request);
        
        return new ResponseEntity<>(new ApiResponse<>(user), HttpStatus.OK);
    }

    @PostMapping("/api/login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@Valid @RequestBody LoginUserDto request) {
        TokenResponseDto token = authService.login(request);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.OK);
    }
    
}
