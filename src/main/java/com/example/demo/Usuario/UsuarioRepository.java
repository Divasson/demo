package com.example.demo.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    //@Query("SELECT s FROM Usuario WHERE s.email = ?1")
    Optional<Usuario> findUsuarioByEmail(String email);

    Optional<Usuario> findUsuarioByCodigoUsuario(Integer codigo_usuario);
}
