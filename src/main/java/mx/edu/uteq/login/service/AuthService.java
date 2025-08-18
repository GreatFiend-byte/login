package mx.edu.uteq.login.service;
import mx.edu.uteq.login.dto.UserResponse;
import mx.edu.uteq.login.dto.LoginRequest;
import mx.edu.uteq.login.dto.LoginResponse;
import mx.edu.uteq.login.model.User;
import mx.edu.uteq.login.repository.UserRepository;
import mx.edu.uteq.login.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import mx.edu.uteq.login.exception.AuthException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        String token = jwtService.generateToken(user);
        
        return LoginResponse.builder()
            .token(token)
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
    }


     public UserResponse validateToken(String token) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        if (!jwtService.isTokenValid(token, user)) {
            throw new AuthException("Token inválido o expirado");
        }
        
        return UserResponse.builder()
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
    }
}