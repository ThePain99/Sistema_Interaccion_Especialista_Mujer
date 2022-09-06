package com.interaction.interactionsystemwoman.dto;

import com.interaction.interactionsystemwoman.entity.*;
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
public class ConsultaDTO {
    private Integer id;
    private LocalDateTime fechaReserva;
    private String descripcion;
    private Integer usuarioId;
    private EstadoConsultaDTO estadoConsulta;
    private PacienteDTO paciente;
    private List<String> violencias = new ArrayList<>();
    private ModalidadDTO modalidad;

}
