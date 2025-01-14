package com.babylo.banksampah.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.babylo.banksampah.dto.UserDto;
import com.babylo.banksampah.dto.UserResponseDto;
import com.babylo.banksampah.entities.User;
import com.babylo.banksampah.repositories.UserRepository;
import com.babylo.banksampah.security.BCrypt;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user;
    }

    public UserResponseDto addUser(UserDto user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        newUser.setFullName(user.getFullName());
        newUser.setRole(user.getRole());
        userRepository.save(newUser);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(newUser.getUsername());
        userResponseDto.setFullName(newUser.getFullName());
        userResponseDto.setRole(newUser.getRole());
        return userResponseDto;
    }

}
