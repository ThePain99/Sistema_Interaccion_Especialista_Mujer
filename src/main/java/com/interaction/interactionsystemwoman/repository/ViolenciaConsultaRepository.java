package com.interaction.interactionsystemwoman.repository;

import com.interaction.interactionsystemwoman.entity.Consulta;
import com.interaction.interactionsystemwoman.entity.Violencia;
import com.interaction.interactionsystemwoman.entity.ViolenciaConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViolenciaConsultaRepository extends JpaRepository<ViolenciaConsulta, Integer> {

    Optional<List<ViolenciaConsulta>> findByConsulta(Consulta consulta);


    void deleteByConsultaAndViolencia(Consulta consulta, Violencia violencia);

    void deleteAllByConsulta(Consulta consulta);
}
