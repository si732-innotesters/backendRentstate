package com.example.rentstate.profiles.shared.services.implementation;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.valueobjects.Account;
import com.example.rentstate.profiles.domain.service.UserService;
import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.rentstate.shared.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getById(Long userId) {
        return Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId)));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> create(User user) {
        String email = user.getAccount().email();
        Optional<User> userExistWithEmail = userRepository.findByAccount_Email(email);
        if (!userExistWithEmail.isEmpty())
            throw new IllegalArgumentException("User with email " + email + " already exists");

        return Optional.of(userRepository.save(user));
    }


    @Override
    public Optional<User> update(User user) {
        return Optional.ofNullable(userRepository.findById(user.getId()).map(userToUpdate ->
                        userRepository.save(userToUpdate
                                .withName(user.getName())
                                .withLastName(user.getLastName())
                                .withAge(user.getAge())
                                .withGender(user.getGender())
                                .withDescription(user.getDescription())
                                .withIsPremium(user.getIsPremium())
                                .withPhotoUrl(user.getPhotoUrl())
                        ))
                .orElseThrow(() -> new ResourceNotFoundException("User", user.getId())));
    }

    @Override
    public ResponseEntity<?> delete(Long userId) {
        return userRepository.findById(userId).map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();})
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }

    @Override
    public Optional<User> login(Account account) {
        Optional<User> user = userRepository.findByAccount(account);

        return user;
    }
}