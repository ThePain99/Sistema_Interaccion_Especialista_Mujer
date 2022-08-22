package com.interaction.interactionsystemwoman.repository;

import com.interaction.interactionsystemwoman.entity.Violencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViolenciaRepository extends JpaRepository<Violencia, Integer> {
}
