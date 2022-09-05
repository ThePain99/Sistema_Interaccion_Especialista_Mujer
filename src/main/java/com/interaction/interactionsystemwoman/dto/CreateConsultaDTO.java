package com.interaction.interactionsystemwoman.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsultaDTO {
    private Integer id;
    private LocalDateTime fechaReserva;
    private String descripcion;
    private Integer usuarioId;
    private Integer estadoConsultaId;
    private Integer pacienteId;
    private List<String> violencias = new ArrayList<>();
    private Integer modalidadId;

}
