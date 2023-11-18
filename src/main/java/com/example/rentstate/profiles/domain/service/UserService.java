package com.example.rentstate.profiles.domain.service;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.valueobjects.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getById(Long userId);
    List<User> getAll();
    Optional<User> create(User user);
    Optional<User> update(User user);
    ResponseEntity<?> delete(Long userId);

    Optional<User> login(Account account);
}