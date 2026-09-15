package com.ticketflow.ticketflow.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class RegisterResponse {
    
    private Long id;
    private String email;

    public RegisterResponse(Long id, String email){
        this.id=id;
        this.email=email;
    }
}
