package com.interaction.interactionsystemwoman.repository;

import com.interaction.interactionsystemwoman.entity.Violencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViolenciaRepository extends JpaRepository<Violencia, Integer> {

    Optional<Violencia> findByViolencia(String violencia);
}
