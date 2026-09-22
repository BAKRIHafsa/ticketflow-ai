package com.ticketflow.ticketflow.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ticketflow.ticketflow.user.entity.AppRole;
import com.ticketflow.ticketflow.user.entity.User;
import com.ticketflow.ticketflow.user.repository.UserRepository;

@Component 
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    
    @Override 
    public void run(String... args){
        if (userRepository.findByEmail(adminEmail).isEmpty()){
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(AppRole.ADMIN);
            
            userRepository.save(admin);
            System.out.println("TicketFlow ADMIN created");
        }
        else{
            System.out.println("TicketFlow ADMIN already exists");
        }
    }
}
