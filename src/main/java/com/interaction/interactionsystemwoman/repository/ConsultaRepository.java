package com.interaction.interactionsystemwoman.repository;

import com.interaction.interactionsystemwoman.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    @Query("select c from Consulta c " +
            "where (c.usuario.id = :usuarioId or :usuarioId is null) " +
            "and (c.paciente.id = :pacienteId or :pacienteId is null) "
    )
    List<Consulta> findConsultaEntity(
            @Param("usuarioId")Integer usuarioId,
            @Param("pacienteId")Integer pacienteId);
}
