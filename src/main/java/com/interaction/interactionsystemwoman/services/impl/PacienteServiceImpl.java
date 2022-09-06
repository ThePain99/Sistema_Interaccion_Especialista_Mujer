package com.interaction.interactionsystemwoman.services.impl;

import com.interaction.interactionsystemwoman.dto.PacienteDTO;
import com.interaction.interactionsystemwoman.entity.*;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;
import com.interaction.interactionsystemwoman.exceptions.InternalServerErrorException;
import com.interaction.interactionsystemwoman.exceptions.NotFoundException;
import com.interaction.interactionsystemwoman.repository.*;
import com.interaction.interactionsystemwoman.services.PacienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    public static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ViolenciaConsultaRepository violenciaConsultaRepository;

    @Override
    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) throws GeneralException {

        Usuario usuario = usuarioRepository.findById(pacienteDTO.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "USUARIO_NOT_FOUND"));

        Paciente paciente = new Paciente();
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setApellido(pacienteDTO.getApellido());
        paciente.setDni(pacienteDTO.getDni());
        paciente.setCorreo(pacienteDTO.getCorreo());
        paciente.setNumero(pacienteDTO.getNumero());
        paciente.setUsuario(usuario);

        try {
            paciente = pacienteRepository.save(paciente);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }
        return modelMapper.map(getPacienteEntity(paciente.getId()), PacienteDTO.class);
    }

    @Override
    public PacienteDTO getPacienteById(Integer id, Integer usuarioId) throws GeneralException {
        PacienteDTO pacienteDTO = modelMapper.map(getPacienteEntity(id), PacienteDTO.class);
        pacienteDTO.setConsultaCount(consultaRepository.countConsultaEntity(usuarioId, id));

        List<Consulta> consultas = consultaRepository.findConsultaEntity(usuarioId, id);
        Set<String> violencias = new HashSet<>();
        for(Consulta consulta : consultas){
            for(ViolenciaConsulta violenciaConsulta : consulta.getViolenciaConsultas()){
                violencias.add(violenciaConsulta.getViolencia().getViolencia());
            }
        }
        pacienteDTO.setViolencias(violencias);
        return pacienteDTO;
    }

    @Override
    public List<PacienteDTO> getPacientes(Integer usuarioId) throws GeneralException {
        List<Paciente> pacientes = pacienteRepository.findPacienteEntity(usuarioId);
        List<PacienteDTO> pacientesDTO = pacientes.stream().map(paciente -> modelMapper.map(paciente, PacienteDTO.class)).collect(Collectors.toList());
        for (PacienteDTO pacienteDTO: pacientesDTO) {
            pacienteDTO.setConsultaCount(consultaRepository.countConsultaEntity(usuarioId, pacienteDTO.getId()));

            List<Consulta> consultas = consultaRepository.findConsultaEntity(usuarioId, pacienteDTO.getId());
            Set<String> violencias = new HashSet<>();
            for(Consulta consulta : consultas){
                for(ViolenciaConsulta violenciaConsulta : consulta.getViolenciaConsultas()){
                    violencias.add(violenciaConsulta.getViolencia().getViolencia());
                }
            }
            pacienteDTO.setViolencias(violencias);
        }
        return pacientesDTO;
    }

    @Override
    public PacienteDTO updatePaciente(PacienteDTO pacienteDTO) throws GeneralException {
        Paciente paciente = getPacienteEntity(pacienteDTO.getId());

        paciente.setNombre(pacienteDTO.getNombre() != null ? pacienteDTO.getNombre() : paciente.getNombre());
        paciente.setApellido(pacienteDTO.getApellido() != null ? pacienteDTO.getApellido() : paciente.getApellido());
        paciente.setDni(pacienteDTO.getDni() != null ? pacienteDTO.getDni() : paciente.getDni());
        paciente.setCorreo(pacienteDTO.getCorreo() != null ? pacienteDTO.getCorreo() : paciente.getCorreo());
        paciente.setNumero(pacienteDTO.getNumero() != null ? pacienteDTO.getNumero() : paciente.getNumero());

        try {
            paciente = pacienteRepository.save(paciente);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }
        return modelMapper.map(getPacienteEntity(paciente.getId()), PacienteDTO.class);
    }

    @Override
    public void deletePaciente(Integer id) throws GeneralException {
        pacienteRepository.deleteById(id);
    }

    private Paciente getPacienteEntity(Integer id) throws GeneralException{
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOT FOUND-404","PACIENTE_NOTFOUND-404"));
    }
}
