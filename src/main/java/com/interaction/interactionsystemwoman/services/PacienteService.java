package com.interaction.interactionsystemwoman.services;

import com.interaction.interactionsystemwoman.dto.PacienteDTO;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;

import java.util.List;

public interface PacienteService {
    
    PacienteDTO createPaciente(PacienteDTO createEntityADto) throws GeneralException;

    PacienteDTO getPacienteById(Integer id, Integer usuarioId) throws GeneralException;

    List<PacienteDTO> getPacientes(Integer usuarioId) throws GeneralException;

    PacienteDTO updatePaciente(PacienteDTO pacienteDTO) throws GeneralException;

    void deletePaciente(Integer id) throws GeneralException;
}
