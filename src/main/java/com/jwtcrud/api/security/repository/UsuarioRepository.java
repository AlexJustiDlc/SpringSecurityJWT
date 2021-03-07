package com.jwtcrud.api.security.repository;

import com.jwtcrud.api.security.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByCorreo(String correo);
}
