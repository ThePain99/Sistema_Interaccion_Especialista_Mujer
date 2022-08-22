package com.interaction.interactionsystemwoman.repository;

import com.interaction.interactionsystemwoman.entity.Modalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadRepository extends JpaRepository<Modalidad, Integer> {
}
