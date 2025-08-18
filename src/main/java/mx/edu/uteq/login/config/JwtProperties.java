package mx.edu.uteq.login.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Validated
public class JwtProperties {
    
    @NotBlank(message = "La propiedad 'jwt.secret' es obligatoria")
    private String secret;
    
    @Min(value = 60000, message = "La expiración debe ser al menos 60000 ms (1 minuto)")
    private long expiration;
    
    @NotBlank(message = "La propiedad 'jwt.header' es obligatoria")
    private String header;
    
    @NotBlank(message = "La propiedad 'jwt.prefix' es obligatoria")
    private String prefix;
    
    @NotBlank(message = "La propiedad 'jwt.type' es obligatoria")
    private String type;

    // Getters y Setters (OBLIGATORIOS)
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}