package com.interaction.interactionsystemwoman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="violenciaconsulta")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViolenciaConsulta {
    @Id
    @SequenceGenerator(
            name = "violenciaconsulta_sequence",
            sequenceName = "violenciaconsulta_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "violenciaconsulta_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "id_Violencia",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="violencia_violenciaconsulta_fk"
            )
    )
    private Violencia violencia;

    @ManyToOne
    @JoinColumn(
            name = "id_Consulta",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="consulta_violenciaconsulta_fk"
            )
    )
    private Consulta consulta;
}
