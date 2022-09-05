package com.interaction.interactionsystemwoman.services.impl;

import com.interaction.interactionsystemwoman.dto.ConsultaDTO;
import com.interaction.interactionsystemwoman.dto.CreateConsultaDTO;
import com.interaction.interactionsystemwoman.dto.PacienteDTO;
import com.interaction.interactionsystemwoman.entity.*;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;
import com.interaction.interactionsystemwoman.exceptions.InternalServerErrorException;
import com.interaction.interactionsystemwoman.exceptions.NotFoundException;
import com.interaction.interactionsystemwoman.repository.*;
import com.interaction.interactionsystemwoman.services.ConsultaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ViolenciaConsultaRepository violenciaConsultaRepository;

    @Override
    public CreateConsultaDTO createConsulta(CreateConsultaDTO consultaDTO) throws GeneralException {
        Usuario usuario = usuarioRepository.findById(consultaDTO.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "USUARIO_NOT_FOUND"));

        EstadoConsulta estadoConsulta = estadoConsultaRepository.findById(consultaDTO.getEstadoConsultaId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "ESTADOCONSULTA_NOT_FOUND"));

        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "PACIENTE_NOT_FOUND"));


        Modalidad modalidad = modalidadRepository.findById(consultaDTO.getModalidadId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "MODALIDAD_NOT_FOUND"));


        Consulta consulta = new Consulta();
        consulta.setFechaReserva(consultaDTO.getFechaReserva());
        consulta.setDescripcion(consultaDTO.getDescripcion());
        consulta.setUsuario(usuario);
        consulta.setEstadoConsulta(estadoConsulta);
        consulta.setPaciente(paciente);
        consulta.setModalidad(modalidad);

        try {
            consulta = consultaRepository.save(consulta);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }

        for (String violenciaNombre : consultaDTO.getViolencias()) {
            Violencia violencia = violenciaRepository.findByViolencia(violenciaNombre)
                    .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "VIOLENCIA_NOT_FOUND"));

            ViolenciaConsulta violenciaConsulta = new ViolenciaConsulta();
            violenciaConsulta.setViolencia(violencia);
            violenciaConsulta.setConsulta(consulta);

            try {
                violenciaConsultaRepository.save(violenciaConsulta);
            } catch (Exception ex) {
                throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
            }
        }

        return toCreateConsultaDto(consulta);
    }

    @Override
    public ConsultaDTO getConsultaById(Integer id) throws GeneralException {
        return toConsultaDto(getConsultaEntity(id));
    }

    @Override
    public List<ConsultaDTO> getConsultas(Integer usuarioId, Integer pacienteId) throws GeneralException {
        List<Consulta> consultas = consultaRepository.findConsultaEntity(usuarioId, pacienteId);
        List<ConsultaDTO> consultasDTO = new ArrayList<>();
        for (Consulta consulta: consultas){
            consultasDTO.add(toConsultaDto(consulta));
        }
        return consultasDTO;
    }

    @Override
    public CreateConsultaDTO updateConsulta(CreateConsultaDTO consultaDTO) throws GeneralException {
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
        return toCreateConsultaDto(getConsultaEntity(consulta.getId()));
    }

    @Override
    public void updateAddViolencia(Integer id, String violenciaNombre) throws GeneralException {
        Violencia violencia = violenciaRepository.findByViolencia(violenciaNombre)
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "VIOLENCIA_NOT_FOUND"));

        ViolenciaConsulta violenciaConsulta = new ViolenciaConsulta();
        violenciaConsulta.setViolencia(violencia);
        violenciaConsulta.setConsulta(getConsultaEntity(id));

        try {
            violenciaConsultaRepository.save(violenciaConsulta);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }
    }

    @Override
    @Transactional
    public void updateRemoveViolencia(Integer id, String violenciaNombre) throws GeneralException {
        Violencia violencia = violenciaRepository.findByViolencia(violenciaNombre)
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "VIOLENCIA_NOT_FOUND"));

        Consulta consulta = getConsultaEntity(id);
        violenciaConsultaRepository.deleteByConsultaAndViolencia(consulta, violencia);
    }

    @Override
    public void deleteConsulta(Integer id) throws GeneralException {
        consultaRepository.deleteById(id);
    }

    private Consulta getConsultaEntity(Integer id) throws GeneralException{
        return consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOT FOUND-404","CONSULTA_NOTFOUND-404"));
    }

    private ConsultaDTO toConsultaDto(Consulta consulta) throws GeneralException {
        ConsultaDTO consultaDTO = modelMapper.map(getConsultaEntity(consulta.getId()), ConsultaDTO.class);
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId().getId())
                .orElseThrow(() -> new NotFoundException("NOT_FOUND-401-1", "PACIENTE_NOT_FOUND"));
        consultaDTO.setPacienteId(modelMapper.map(paciente, PacienteDTO.class));

        List<ViolenciaConsulta> violenciaConsultas = violenciaConsultaRepository.findByConsulta(consulta).get();
        List<String> violencias = new ArrayList<>();
        for(ViolenciaConsulta violenciaConsulta: violenciaConsultas){
            violencias.add(violenciaConsulta.getViolencia().getViolencia());
        }
        consultaDTO.setViolencias(violencias);

        return consultaDTO;
    }

    private CreateConsultaDTO toCreateConsultaDto(Consulta consulta) throws GeneralException {
        CreateConsultaDTO consultaDTO = modelMapper.map(getConsultaEntity(consulta.getId()), CreateConsultaDTO.class);

        List<ViolenciaConsulta> violenciaConsultas = violenciaConsultaRepository.findByConsulta(consulta).get();
        List<String> violencias = new ArrayList<>();
        for(ViolenciaConsulta violenciaConsulta: violenciaConsultas){
            violencias.add(violenciaConsulta.getViolencia().getViolencia());
        }
        consultaDTO.setViolencias(violencias);

        return consultaDTO;
    }
}
