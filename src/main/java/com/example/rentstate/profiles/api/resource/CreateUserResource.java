package com.example.rentstate.profiles.api.resource;

import com.example.rentstate.profiles.domain.model.valueobjects.Account;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateUserResource {
    private String name;
    private String lastName;
    private String email;
    private String password;
}
