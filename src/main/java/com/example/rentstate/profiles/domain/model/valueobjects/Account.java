package com.example.rentstate.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Account(String email, String password) {

    public Account(){
        this(null,null);
    }
    public Account{
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
    }
}