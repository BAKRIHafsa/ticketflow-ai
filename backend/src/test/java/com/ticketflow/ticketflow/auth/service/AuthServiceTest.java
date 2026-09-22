package com.ticketflow.ticketflow.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ticketflow.ticketflow.auth.dto.LoginRequest;
import com.ticketflow.ticketflow.user.entity.User;
import com.ticketflow.ticketflow.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    
    @Mock 
    private UserService userService;

    @Mock 
    private PasswordEncoder passwordEncoder;

    @Mock 
    private JwtService jwtService;

    @InjectMocks 
    private AuthService authService;

    private User user;

    private LoginRequest loginRequest;

    @BeforeEach 
    void SetUp(){
        user = new User();
        user.setEmail("userTest@test.com");
        user.setPassword("hashed-password");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("userTest@test.com");
        loginRequest.setPassword("password123");
    }

    @Test 
    void shouldLoginSuccessfully() {
        when(userService.findByEmail("userTest@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashed-password")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("fake-jwt-token");

        String token = authService.login(loginRequest);
        assertEquals("fake-jwt-token", token);
        verify(userService).findByEmail("userTest@test.com");
        verify(passwordEncoder).matches("password123", "hashed-password");
        verify(jwtService).generateToken(user);
    }

    @Test
    void shouldRejectLoginWhenEmailDoesNotExist() {
        when(userService.findByEmail("userTest@test.com")).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> authService.login(loginRequest));

        assertEquals("Invalid email or password", exception.getMessage());
        verify(userService).findByEmail("userTest@test.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void shouldRejectLoginWhenPasswordIsIncorrect() {
        when(userService.findByEmail("userTest@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashed-password")).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> authService.login(loginRequest));

        assertEquals("Invalid email or password", exception.getMessage());
        verify(userService).findByEmail("userTest@test.com");
        verify(passwordEncoder).matches("password123", "hashed-password");
        verify(jwtService, never()).generateToken(any());
    }
}
