package com.interaction.interactionsystemwoman.services;

import com.interaction.interactionsystemwoman.dto.ConsultaDTO;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;

import java.util.List;

public interface ConsultaService {

    ConsultaDTO createConsulta(ConsultaDTO createEntityADto) throws GeneralException;

    ConsultaDTO getConsultaById(Integer id) throws GeneralException;

    List<ConsultaDTO> getConsultas(Integer usuarioId, Integer pacienteId) throws GeneralException;

    ConsultaDTO updateConsulta(ConsultaDTO consultaDTO) throws GeneralException;

    void updateAddViolencia(Integer id, String violenciaNombre) throws GeneralException;

    void updateRemoveViolencia(Integer id, String violenciaNombre) throws GeneralException;

    void deleteConsulta(Integer id) throws GeneralException;
}
