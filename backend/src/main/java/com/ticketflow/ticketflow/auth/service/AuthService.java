package com.ticketflow.ticketflow.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ticketflow.ticketflow.auth.dto.LoginRequest;
import com.ticketflow.ticketflow.auth.dto.RegisterRequest;
import com.ticketflow.ticketflow.user.entity.User;
import com.ticketflow.ticketflow.user.service.UserService;

@Service 
public class AuthService {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }

    public User register(RegisterRequest request){
        
        if (userService.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Emaol already registred");
        }
        User user = new User ();
        user.setEmail(request.getEmail());
        String hashedPassword=passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        return userService.save(user);
    }

    public String login(LoginRequest request){
        
        User user=userService.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }
        return jwtService.generateToken(user.getEmail());
    }
}
