package com.interaction.interactionsystemwoman.services.impl;

import com.interaction.interactionsystemwoman.dto.PacienteDTO;
import com.interaction.interactionsystemwoman.entity.Paciente;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;
import com.interaction.interactionsystemwoman.exceptions.InternalServerErrorException;
import com.interaction.interactionsystemwoman.exceptions.NotFoundException;
import com.interaction.interactionsystemwoman.repository.PacienteRepository;
import com.interaction.interactionsystemwoman.services.PacienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    public static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) throws GeneralException {

        Paciente paciente = new Paciente();
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setApellido(pacienteDTO.getApellido());
        paciente.setDni(pacienteDTO.getDni());
        paciente.setCorreo(pacienteDTO.getCorreo());
        paciente.setNumero(pacienteDTO.getNumero());

        try {
            paciente = pacienteRepository.save(paciente);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }
        return modelMapper.map(getPacienteEntity(paciente.getId()), PacienteDTO.class);
    }

    @Override
    public PacienteDTO getPacienteById(Integer id) throws GeneralException {
        return modelMapper.map(getPacienteEntity(id), PacienteDTO.class);
    }

    @Override
    public List<PacienteDTO> getPacientes() throws GeneralException {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream().map(paciente -> modelMapper.map(paciente, PacienteDTO.class)).collect(Collectors.toList());
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
