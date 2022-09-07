package com.interaction.interactionsystemwoman.repository;


import com.interaction.interactionsystemwoman.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    @Query("select distinct p from Paciente p " +
            "where (p.usuario.id = :usuarioId or :usuarioId is null) "
    )
    List<Paciente> findPacienteEntity(
            @Param("usuarioId")Integer usuarioId);
}
