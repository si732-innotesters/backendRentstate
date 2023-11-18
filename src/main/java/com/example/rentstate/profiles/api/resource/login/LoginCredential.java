package com.example.rentstate.profiles.api.resource.login;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginCredential {
    String email;
    String password;
}