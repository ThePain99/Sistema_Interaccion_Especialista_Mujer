package com.interaction.interactionsystemwoman.services;

import com.interaction.interactionsystemwoman.dto.UsuarioDTO;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;

import java.util.List;

public interface UsuarioService {

    UsuarioDTO createUsuario(UsuarioDTO createEntityADto) throws GeneralException;

    UsuarioDTO getUsuarioById(Integer id) throws GeneralException;

    List<UsuarioDTO> getUsuarios() throws GeneralException;

    UsuarioDTO updateUsuario(UsuarioDTO UsuarioDTO) throws GeneralException;

    void deleteUsuario(Integer id) throws GeneralException;
}
