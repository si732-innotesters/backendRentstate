package com.example.rentstate.profiles.api.rest;

import com.example.rentstate.profiles.api.resource.login.LoginCredential;
import com.example.rentstate.profiles.api.resource.userresource.CreateUserResource;
import com.example.rentstate.profiles.api.resource.userresource.ResourceUserResponse;
import com.example.rentstate.profiles.api.resource.userresource.UpdateUserResource;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.valueobjects.Account;
import com.example.rentstate.profiles.domain.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<ResourceUserResponse> createUser(@RequestBody CreateUserResource createUserResource) {;
        User newUser = modelMapper.map(createUserResource, User.class);

        Account account = new Account(createUserResource.getEmail(), createUserResource.getPassword());
        newUser.setAccount(account);

        Optional<User> createdUser = userService.create(newUser);

        if (createdUser.isPresent()) {
            ResourceUserResponse responseUserResource = new ResourceUserResponse(createdUser.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseUserResource);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public List<ResourceUserResponse> getAllUsers() {
        List<User> users = userService.getAll();
        List<ResourceUserResponse> responseUsers= users.stream()
                .map(user -> modelMapper.map(user, ResourceUserResponse.class))
                .collect(Collectors.toList());

        return responseUsers;
    }

    @GetMapping("{userId}")
    public ResponseEntity<ResourceUserResponse> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getById(userId);

        ResourceUserResponse userResponse = new ResourceUserResponse(user.get());
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping
    public ResponseEntity<ResourceUserResponse> updateUser(@RequestBody UpdateUserResource updateUserResource) {

        User userToUpdate = modelMapper.map(updateUserResource, User.class);

        Optional<User> userUpdate = userService.update(userToUpdate);

        if(userUpdate.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResourceUserResponse userResponse = new ResourceUserResponse(userUpdate.get());
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ResourceUserResponse> login(@RequestBody LoginCredential loginCredential) {

        Account account = new Account(loginCredential.getEmail(), loginCredential.getPassword());

        Optional<User> user = userService.login(account);

        return ResponseEntity.ok(new ResourceUserResponse(user.get()));
    }
}