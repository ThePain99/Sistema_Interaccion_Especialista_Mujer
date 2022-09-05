package com.interaction.interactionsystemwoman.services;

import com.interaction.interactionsystemwoman.dto.ConsultaDTO;
import com.interaction.interactionsystemwoman.dto.CreateConsultaDTO;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;

import java.util.List;

public interface ConsultaService {

    CreateConsultaDTO createConsulta(CreateConsultaDTO createEntityADto) throws GeneralException;

    ConsultaDTO getConsultaById(Integer id) throws GeneralException;

    List<ConsultaDTO> getConsultas(Integer usuarioId, Integer pacienteId) throws GeneralException;

    CreateConsultaDTO updateConsulta(CreateConsultaDTO consultaDTO) throws GeneralException;

    void updateAddViolencia(Integer id, String violenciaNombre) throws GeneralException;

    void updateRemoveViolencia(Integer id, String violenciaNombre) throws GeneralException;

    void deleteConsulta(Integer id) throws GeneralException;
}
