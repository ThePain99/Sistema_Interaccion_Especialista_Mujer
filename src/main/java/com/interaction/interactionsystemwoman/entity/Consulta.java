package com.interaction.interactionsystemwoman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="consulta")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "fecha_Reserva",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime fechaReserva;

    @Column(
            name = "descripcion",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String descripcion;

    @ManyToOne
    @JoinColumn(
            name = "id_Usuario",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="usuario_consulta_fk"
            )
    )
    /*@Column(
            name = "idUsuario",
            nullable = false
    )*/
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(
            name = "id_EstadoConsulta",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="estadoconsulta_consulta_fk"
            )
    )
    /*@Column(
            name = "idEstadoConsulta",
            nullable = false
    )*/
    private EstadoConsulta estadoConsulta;

    @ManyToOne
    @JoinColumn(
            name = "id_Paciente",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="paciente_consulta_fk"
            )
    )
    /*@Column(
            name = "idPaciente",
            nullable = false
    )*/
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(
            name = "id_Violencia",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="violencia_consulta_fk"
            )
    )
    /*@Column(
            name = "idViolencia",
            nullable = false
    )*/
    private Violencia violencia;

    @ManyToOne
    @JoinColumn(
            name = "id_Modalidad",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="modalidad_consulta_fk"
            )
    )
    /*@Column(
            name = "idModalidad",
            nullable = false
    )*/
    private Modalidad modalidad;
}
