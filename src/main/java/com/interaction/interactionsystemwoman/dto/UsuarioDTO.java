package com.interaction.interactionsystemwoman.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    private String contrasena;
    private Boolean estado;
    private Boolean tipo;
    private Integer modalidadId;
}
