package com.example.rentstate.controllers;


import com.example.rentstate.domain.model.User;
import com.example.rentstate.domain.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return  userService.getAll();
    }

    @GetMapping("{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getById(userId);
    }
    @PostMapping
    public User createUser(@RequestBody User userResource) {
        return userService.create(userResource);
    }
}
