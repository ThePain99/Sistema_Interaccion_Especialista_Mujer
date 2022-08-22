package com.interaction.interactionsystemwoman.repository;

import com.interaction.interactionsystemwoman.entity.EstadoConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoConsultaRepository extends JpaRepository<EstadoConsulta, Integer> {
}
