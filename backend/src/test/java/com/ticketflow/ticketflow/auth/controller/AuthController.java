package com.ticketflow.ticketflow.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketflow.ticketflow.auth.dto.LoginRequest;
import com.ticketflow.ticketflow.auth.dto.LoginResponse;
import com.ticketflow.ticketflow.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController 
@Tag(name="Authentication", description = "Authentication endpoints")
@RequestMapping ("/api/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    /* @PostMapping ("/register")
    @Operation(summary = "Register a new user", description = "Creates a new TicketFlow user account")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request){
        User user=authService.register(request);
        RegisterResponse response=new RegisterResponse(user.getId(),user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } */

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Authenticate and receive a JWT")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){  
        String token = authService.login(request);
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

}
