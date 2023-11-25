package com.example.rentstate.profiles.domain.service;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getById(Long userId);
    List<User> getAll();
    Optional<User> update(User user);
    ResponseEntity<?> delete(Long userId);

}