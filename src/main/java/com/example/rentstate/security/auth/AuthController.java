package com.example.rentstate.security.auth;
import com.example.rentstate.security.auth.resource.AuthResponse;
import com.example.rentstate.security.auth.resource.LoginRequest;
import com.example.rentstate.security.auth.resource.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200","https://renstate2-0.web.app"})
@RestController
@RequestMapping("/auth/api")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(
                authService.login(loginRequest)
        );

    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(
                authService.register(registerRequest)
        );
    }

}
