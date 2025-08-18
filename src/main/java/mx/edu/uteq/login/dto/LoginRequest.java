package mx.edu.uteq.login.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String email;
    
    private String password; // Asegúrate que no haya espacios con @NotBlank
    
    // Añade trim() en el getter
    public String getPassword() {
        return password != null ? password.trim() : null;
    }
}