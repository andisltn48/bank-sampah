package com.babylo.banksampah.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.babylo.banksampah.dto.LoginUserDto;
import com.babylo.banksampah.dto.TokenResponseDto;
import com.babylo.banksampah.dto.UserDto;
import com.babylo.banksampah.dto.UserResponseDto;
import com.babylo.banksampah.entities.User;
import com.babylo.banksampah.exception.UnauthorizedException;
import com.babylo.banksampah.repositories.UserRepository;
import com.babylo.banksampah.security.BCrypt;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public UserResponseDto register(UserDto user) {
        return userService.addUser(user);
    }

    public TokenResponseDto login(LoginUserDto user) {
        User userData = userRepository.findByUsername(user.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("Username or password wrong"));

        if (BCrypt.checkpw(user.getPassword(), userData.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());
            TokenResponseDto tokenResponseDto = new TokenResponseDto();
            tokenResponseDto.setBearerToken(token);

            return tokenResponseDto;
        } else {
            throw new UnauthorizedException("Username or password wrong");
        }
    }
}
