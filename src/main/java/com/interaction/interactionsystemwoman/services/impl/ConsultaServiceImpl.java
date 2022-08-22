package com.interaction.interactionsystemwoman.services.impl;

import com.interaction.interactionsystemwoman.dto.ConsultaDTO;
import com.interaction.interactionsystemwoman.entity.*;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;
import com.interaction.interactionsystemwoman.exceptions.InternalServerErrorException;
import com.interaction.interactionsystemwoman.exceptions.NotFoundException;
import com.interaction.interactionsystemwoman.repository.*;
import com.interaction.interactionsystemwoman.services.ConsultaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    public static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private EstadoConsultaRepository estadoConsultaRepository;
    @Autowired
    private ModalidadRepository modalidadRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ViolenciaRepository violenciaRepository;

    @Override
    public ConsultaDTO createConsulta(ConsultaDTO consultaDTO) throws GeneralException {
        Usuario usuario = usuarioRepository.findById(consultaDTO.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "USUARIO_NOT_FOUND"));

        EstadoConsulta estadoConsulta = estadoConsultaRepository.findById(consultaDTO.getEstadoConsultaId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "ESTADOCONSULTA_NOT_FOUND"));

        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "PACIENTE_NOT_FOUND"));

        Violencia violencia = violenciaRepository.findById(consultaDTO.getViolenciaId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "VIOLENCIA_NOT_FOUND"));

        Modalidad modalidad = modalidadRepository.findById(consultaDTO.getModalidadId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "MODALIDAD_NOT_FOUND"));


        Consulta consulta = new Consulta();
        consulta.setFechaReserva(consultaDTO.getFechaReserva());
        consulta.setDescripcion(consultaDTO.getDescripcion());
        consulta.setUsuario(usuario);
        consulta.setEstadoConsulta(estadoConsulta);
        consulta.setPaciente(paciente);
        consulta.setViolencia(violencia);
        consulta.setModalidad(modalidad);

        try {
            consulta = consultaRepository.save(consulta);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }
        return modelMapper.map(getConsultaEntity(consulta.getId()), ConsultaDTO.class);
    }

    @Override
    public ConsultaDTO getConsultaById(Integer id) throws GeneralException {
        return modelMapper.map(getConsultaEntity(id), ConsultaDTO.class);
    }

    @Override
    public List<ConsultaDTO> getConsultas() throws GeneralException {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream().map(consulta -> modelMapper.map(consulta, ConsultaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ConsultaDTO updateConsulta(ConsultaDTO consultaDTO) throws GeneralException {
        Consulta consulta = getConsultaEntity(consultaDTO.getId());

        if(consultaDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(consultaDTO.getUsuarioId())
                    .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "USUARIO_NOT_FOUND"));
            consulta.setUsuario(usuario);
        }
        if(consultaDTO.getEstadoConsultaId() != null){
            EstadoConsulta estadoConsulta = estadoConsultaRepository.findById(consultaDTO.getEstadoConsultaId())
                    .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "ESTADOCONSULTA_NOT_FOUND"));
            consulta.setEstadoConsulta(estadoConsulta);
        }
        if(consultaDTO.getPacienteId() != null){
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "PACIENTE_NOT_FOUND"));
            consulta.setPaciente(paciente);
        }
        if(consultaDTO.getViolenciaId() != null){
            Violencia violencia = violenciaRepository.findById(consultaDTO.getViolenciaId())
                    .orElseThrow(()-> new NotFoundException("NOT_FOUND-401-1", "VIOLENCIA_NOT_FOUND"));
            consulta.setViolencia(violencia);
        }
        if(consultaDTO.getModalidadId() != null) {
            Modalidad modalidad = modalidadRepository.findById(consultaDTO.getModalidadId())
                    .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "MODALIDAD_NOT_FOUND"));
            consulta.setModalidad(modalidad);
        }

        consulta.setFechaReserva(consultaDTO.getFechaReserva() != null ? consultaDTO.getFechaReserva() : consulta.getFechaReserva());
        consulta.setDescripcion(consultaDTO.getDescripcion() != null ? consultaDTO.getDescripcion() : consulta.getDescripcion());

        try {
            consulta = consultaRepository.save(consulta);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }
        return modelMapper.map(getConsultaEntity(consulta.getId()), ConsultaDTO.class);
    }

    @Override
    public void deleteConsulta(Integer id) throws GeneralException {
        consultaRepository.deleteById(id);
    }

    private Consulta getConsultaEntity(Integer id) throws GeneralException{
        return consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOT FOUND-404","CONSULTA_NOTFOUND-404"));
    }
}
