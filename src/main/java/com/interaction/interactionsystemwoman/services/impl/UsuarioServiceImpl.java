package com.interaction.interactionsystemwoman.services.impl;

import com.interaction.interactionsystemwoman.dto.UsuarioDTO;
import com.interaction.interactionsystemwoman.entity.Modalidad;
import com.interaction.interactionsystemwoman.entity.Usuario;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;
import com.interaction.interactionsystemwoman.exceptions.InternalServerErrorException;
import com.interaction.interactionsystemwoman.exceptions.NotFoundException;
import com.interaction.interactionsystemwoman.repository.ConsultaRepository;
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

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) throws GeneralException {
        Modalidad modalidad;

        if(usuarioDTO.getModalidadId() != null) {
            modalidad = modalidadRepository.findById(usuarioDTO.getModalidadId())
                    .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "MODALIDAD_NOT_FOUND"));
        } else {
            modalidad = null;
        }

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

        UsuarioDTO returnUsuarioDTO = modelMapper.map(getUsuarioEntity(usuario.getId()), UsuarioDTO.class);
        returnUsuarioDTO.setContrasena(null);
        return returnUsuarioDTO;
    }

    @Override
    public UsuarioDTO getUsuarioById(Integer id) throws GeneralException {
        UsuarioDTO returnUsuarioDTO = modelMapper.map(getUsuarioEntity(id), UsuarioDTO.class);
        returnUsuarioDTO.setContrasena(null);
        returnUsuarioDTO.setConsultaCount(consultaRepository.countConsultaEntity(id, null));
        return returnUsuarioDTO;
    }

    @Override
    public UsuarioDTO getUsuarioByCorreoandContrasena(String correo, String contrasena) throws GeneralException {
        Usuario usuario = usuarioRepository.findByCorreoAndContrasena(correo, contrasena)
                .orElseThrow(() -> new NotFoundException("NOT FOUND-404","USUARIO_NOTFOUND-404"));
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        usuarioDTO.setContrasena(null);
        return usuarioDTO;
    }

    @Override
    public UsuarioDTO getUsuarioByCorreo(String correo) throws GeneralException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new NotFoundException("NOT FOUND-404","USUARIO_NOTFOUND-404"));
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        usuarioDTO.setContrasena(null);
        return usuarioDTO;
    }

    @Override
    public List<UsuarioDTO> getUsuarios() throws GeneralException {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).collect(Collectors.toList());
        for(UsuarioDTO usuarioDTO: usuariosDTO){
            usuarioDTO.setConsultaCount(consultaRepository.countConsultaEntity(usuarioDTO.getId(), null));
        }
        return usuariosDTO;
    }

    @Override
    public UsuarioDTO updateUsuario(UsuarioDTO usuarioDTO) throws GeneralException {
        Usuario usuario = getUsuarioEntity(usuarioDTO.getId());

        Modalidad modalidad;

        if(usuarioDTO.getModalidadId() != null) {
            modalidad = modalidadRepository.findById(usuarioDTO.getModalidadId())
                    .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "MODALIDAD_NOT_FOUND"));
        } else {
            modalidad = null;
        }

        usuario.setModalidad(modalidad);
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
        UsuarioDTO returnUsuarioDTO = modelMapper.map(getUsuarioEntity(usuario.getId()), UsuarioDTO.class);
        returnUsuarioDTO.setContrasena(null);
        return returnUsuarioDTO;
    }

    @Override
    public void deleteUsuario(Integer id) throws GeneralException {
        usuarioRepository.deleteById(id);
    }

    private Usuario getUsuarioEntity(Integer id) throws GeneralException{
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOT FOUND-404","USUARIO_NOTFOUND-404"));
    }
}
