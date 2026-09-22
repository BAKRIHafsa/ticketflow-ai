package com.ticketflow.ticketflow.auth.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.ticketflow.ticketflow.user.entity.AppRole;
import com.ticketflow.ticketflow.user.entity.User;

class JwtServiceTest {
    private JwtService jwtService;
    private User user;

    @BeforeEach
    void setUp(){
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretkey","ticketflow-secret-key-ticketflow-secret-key-123456");
        ReflectionTestUtils.setField(jwtService, "expirationTime",Duration.ofHours(1));
        user = new User();
        user.setEmail("admin@ticketflow.local");
        user.setRole(AppRole.ADMIN);

    }

    @Test
    void shouldGenerateToken(){
        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldExtractEmailFromToken() {

        String token = jwtService.generateToken(user);

        String email = jwtService.extractEmail(token);

        assertEquals("admin@ticketflow.local", email);
    }

    @Test
    void shouldValidateTokenForCorrectUser() {

        String token = jwtService.generateToken(user);

        boolean valid = jwtService.isTokenValid(token, user);

        assertTrue(valid);
    }

    @Test
    void shouldRejectTokenForDifferentUser() {

        String token = jwtService.generateToken(user);

        User anotherUser = new User();
        anotherUser.setEmail("customer@ticketflow.local");
        anotherUser.setRole(AppRole.CUSTOMER);

        boolean valid = jwtService.isTokenValid(token, anotherUser);

        assertFalse(valid);
    }
}
