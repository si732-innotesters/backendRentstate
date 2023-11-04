package com.example.rentstate.shared.service;

import com.example.rentstate.domain.model.User;
import com.example.rentstate.domain.persistence.interfaces.UserRepository;
import com.example.rentstate.domain.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }
}
