package com.interaction.interactionsystemwoman.services.impl;

import com.interaction.interactionsystemwoman.dto.ConsultaDTO;
import com.interaction.interactionsystemwoman.dto.UsuarioDTO;
import com.interaction.interactionsystemwoman.entity.Consulta;
import com.interaction.interactionsystemwoman.entity.Modalidad;
import com.interaction.interactionsystemwoman.entity.Usuario;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;
import com.interaction.interactionsystemwoman.exceptions.InternalServerErrorException;
import com.interaction.interactionsystemwoman.exceptions.NotFoundException;
import com.interaction.interactionsystemwoman.repository.ModalidadRepository;
import com.interaction.interactionsystemwoman.repository.UsuarioRepository;
import com.interaction.interactionsystemwoman.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    public static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModalidadRepository modalidadRepository;

    @Override
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) throws GeneralException {
        Modalidad modalidad = modalidadRepository.findById(usuarioDTO.getModalidadId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "MODALIDAD_NOT_FOUND"));

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setDni(usuarioDTO.getDni());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setEstado(usuarioDTO.getEstado());
        usuario.setTipo(usuarioDTO.getTipo());
        usuario.setModalidad(modalidad);

        try {
            usuario = usuarioRepository.save(usuario);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }
        return modelMapper.map(getUsuarioEntity(usuario.getId()), UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO getUsuarioById(Integer id) throws GeneralException {
        return modelMapper.map(getUsuarioEntity(id), UsuarioDTO.class);
    }

    @Override
    public List<UsuarioDTO> getUsuarios() throws GeneralException {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO updateUsuario(UsuarioDTO usuarioDTO) throws GeneralException {
        Usuario usuario = getUsuarioEntity(usuarioDTO.getId());

        if(usuarioDTO.getModalidadId() != null) {
            Modalidad modalidad = modalidadRepository.findById(usuarioDTO.getModalidadId())
                    .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "MODALIDAD_NOT_FOUND"));
            usuario.setModalidad(modalidad);
        }

        usuario.setNombre(usuarioDTO.getNombre() != null ? usuarioDTO.getNombre() : usuario.getNombre());
        usuario.setApellido(usuarioDTO.getApellido() != null ? usuarioDTO.getApellido() : usuario.getApellido());
        usuario.setDni(usuarioDTO.getDni() != null ? usuarioDTO.getDni() : usuario.getDni());
        usuario.setCorreo(usuarioDTO.getCorreo() != null ? usuarioDTO.getCorreo() : usuario.getCorreo());
        usuario.setContrasena(usuarioDTO.getContrasena() != null ? usuarioDTO.getContrasena() : usuario.getContrasena());
        usuario.setEstado(usuarioDTO.getEstado() != null ? usuarioDTO.getEstado() : usuario.getEstado());
        usuario.setTipo(usuarioDTO.getTipo() != null ? usuarioDTO.getTipo() : usuario.getTipo());

        try {
            usuario = usuarioRepository.save(usuario);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }
        return modelMapper.map(getUsuarioEntity(usuario.getId()), UsuarioDTO.class);    }

    @Override
    public void deleteUsuario(Integer id) throws GeneralException {
        usuarioRepository.deleteById(id);
    }

    private Usuario getUsuarioEntity(Integer id) throws GeneralException{
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOT FOUND-404","CONSULTA_NOTFOUND-404"));
    }
}
