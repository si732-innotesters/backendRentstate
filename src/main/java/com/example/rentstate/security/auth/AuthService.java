package com.example.rentstate.security.auth;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.rentstate.security.auth.resource.AuthResponse;
import com.example.rentstate.security.auth.resource.LoginRequest;
import com.example.rentstate.security.auth.resource.RegisterRequest;
import com.example.rentstate.security.config.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        UserDetails userDetails = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();

        String token= jwtService.getToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .userId(userRepository.findByUsername(loginRequest.getUsername()).get().getId())
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        User user =  new User(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .userId(user.getId())
                .build();

    }
}
