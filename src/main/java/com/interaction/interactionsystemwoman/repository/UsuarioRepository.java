package com.interaction.interactionsystemwoman.repository;

import com.interaction.interactionsystemwoman.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreoAndContrasena(String Correo, String Contrasena);

}
