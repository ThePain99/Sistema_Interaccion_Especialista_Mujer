package com.interaction.interactionsystemwoman.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    private Integer numero;
    private Integer usuarioId;
    private Set<String> violencias = new HashSet<>();
    private Long consultaCount;
}
