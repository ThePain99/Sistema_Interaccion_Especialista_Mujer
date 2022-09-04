package com.interaction.interactionsystemwoman.controller;

import com.interaction.interactionsystemwoman.dto.PacienteDTO;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;
import com.interaction.interactionsystemwoman.responses.GeneralResponse;
import com.interaction.interactionsystemwoman.services.ConsultaService;
import com.interaction.interactionsystemwoman.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/interaction/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ConsultaService consultaService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping()
    public GeneralResponse<PacienteDTO> createPaciente (@RequestBody PacienteDTO PacienteDTO) throws GeneralException {
        return new GeneralResponse<>("Success",String.valueOf(HttpStatus.OK), "OK",
                pacienteService.createPaciente(PacienteDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GeneralResponse<PacienteDTO> getPacienteById(
            @PathVariable Integer id,
            @RequestParam(name = "usuarioId", required = false) Integer usuarioId
    ) throws GeneralException{
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                pacienteService.getPacienteById(id, usuarioId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public GeneralResponse<List<PacienteDTO>> getPacientes(
            @RequestParam(name = "usuarioId", required = false) Integer usuarioId
            ) throws GeneralException {
        return new GeneralResponse<>("Success",String.valueOf(HttpStatus.OK),"OKAY",
                pacienteService.getPacientes(usuarioId));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping()
    public GeneralResponse<PacienteDTO> updatePaciente(@RequestBody PacienteDTO pacienteDTO) throws GeneralException{
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                pacienteService.updatePaciente(pacienteDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public GeneralResponse<String> deletePaciente(@PathVariable Integer id) throws GeneralException{
        pacienteService.deletePaciente(id);
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                "Delete OK");
    }
}
