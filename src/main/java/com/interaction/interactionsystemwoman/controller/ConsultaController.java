package com.interaction.interactionsystemwoman.controller;

import com.interaction.interactionsystemwoman.dto.ConsultaDTO;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;
import com.interaction.interactionsystemwoman.responses.GeneralResponse;
import com.interaction.interactionsystemwoman.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/interaction/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping()
    public GeneralResponse<ConsultaDTO> createConsulta (@RequestBody ConsultaDTO consultaDTO) throws GeneralException {
        return new GeneralResponse<>("Success",String.valueOf(HttpStatus.OK), "OK",
                consultaService.createConsulta(consultaDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GeneralResponse<ConsultaDTO> getConsultaById(@PathVariable Integer id) throws GeneralException{
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                consultaService.getConsultaById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public GeneralResponse<List<ConsultaDTO>> getConsultas(
            @RequestParam(name = "usuarioId", required = false) Integer usuarioId,
            @RequestParam(name = "pacienteId", required = false) Integer pacienteId
    ) throws GeneralException {
        return new GeneralResponse<>("Success",String.valueOf(HttpStatus.OK),"OKAY",
                consultaService.getConsultas(usuarioId, pacienteId));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping()
    public GeneralResponse<ConsultaDTO> updateConsulta(@RequestBody ConsultaDTO consultaDTO) throws GeneralException{
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                consultaService.updateConsulta(consultaDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public GeneralResponse<String> deleteConsulta(@PathVariable Integer id) throws GeneralException{
        consultaService.deleteConsulta(id);
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                "Delete OK");
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/violencias/{id}")
    public GeneralResponse<String> updateAddViolencia(
            @PathVariable Integer id,
            @RequestParam(name = "violenciaNombre", required = true) String violenciaNombre
    ) throws GeneralException{

        consultaService.updateAddViolencia(id, violenciaNombre);
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                "Update OK");
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/violencias/{id}")
    public GeneralResponse<String> updateRemoveViolencia(
            @PathVariable Integer id,
            @RequestParam(name = "violenciaNombre", required = true) String violenciaNombre
    ) throws GeneralException{

        consultaService.updateRemoveViolencia(id, violenciaNombre);
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                "Update OK");
    }
}
