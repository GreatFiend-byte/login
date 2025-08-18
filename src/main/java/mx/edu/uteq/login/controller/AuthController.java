package mx.edu.uteq.login.controller;

import mx.edu.uteq.login.dto.LoginRequest;
import mx.edu.uteq.login.dto.LoginResponse;
import mx.edu.uteq.login.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import mx.edu.uteq.login.dto.UserResponse;
import mx.edu.uteq.login.exception.AuthException;
import mx.edu.uteq.login.repository.UserRepository;
import mx.edu.uteq.login.model.User;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/debug/password")
    public String debugPassword(@RequestParam String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return "Contraseña almacenada: " + user.getPassword() +
                "\nMatches '12345678': " + passwordEncoder.matches("12345678", user.getPassword()) +
                "\npassword generado: " + passwordEncoder.encode("12345678");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken())
                .body(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<UserResponse> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthException("Token inválido");
        }

        String token = authHeader.substring(7);
        UserResponse user = authService.validateToken(token);
        return ResponseEntity.ok(user);
    }

}