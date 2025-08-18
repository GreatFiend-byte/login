package mx.edu.uteq.login.repository;

import mx.edu.uteq.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Buscar usuario por email (username)
    Optional<User> findByEmail(String email);
    
    // Verificar si existe un usuario con el email especificado
    boolean existsByEmail(String email);
    
}