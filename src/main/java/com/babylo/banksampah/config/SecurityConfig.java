package com.babylo.banksampah.config;
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.babylo.banksampah.filter.JwtAuthFilter;
import com.babylo.banksampah.repositories.UserRepository;
import com.babylo.banksampah.services.UserService;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 
    private final JwtAuthFilter authFilter; 
    public SecurityConfig(JwtAuthFilter authFilter) { 
        this.authFilter = authFilter; 
    }
    // User Creation 
    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) { 
        return new UserService(repository); 
    } 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception { 
        return http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/login", "/api/register").permitAll()
                .requestMatchers("/api/v1/**").authenticated()
            )
            .httpBasic(withDefaults()).csrf((csrf) -> csrf.disable())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}