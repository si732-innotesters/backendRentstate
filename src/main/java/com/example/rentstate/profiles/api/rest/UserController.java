package com.example.rentstate.profiles.api.rest;


import com.example.rentstate.profiles.api.resource.CreateUserResource;
import com.example.rentstate.profiles.api.resource.ResponseUserResource;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.valueobjects.Account;
import com.example.rentstate.profiles.domain.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping
    public ResponseEntity<ResponseUserResource> createUser(@RequestBody CreateUserResource createUserResource) {
        ModelMapper modelMapper = new ModelMapper();
        User newUser = modelMapper.map(createUserResource, User.class);

        Account account = new Account(createUserResource.getEmail(), createUserResource.getPassword());
        newUser.setAccount(account);

        Optional<User> createdUser = userService.create(newUser);

        if (createdUser.isPresent()) {
            ResponseUserResource responseUserResource = new ResponseUserResource(createdUser.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseUserResource);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
