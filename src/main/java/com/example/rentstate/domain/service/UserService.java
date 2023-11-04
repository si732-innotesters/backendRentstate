package com.example.rentstate.domain.service;

import com.example.rentstate.domain.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(Long userId);

    User create(User user);
}
