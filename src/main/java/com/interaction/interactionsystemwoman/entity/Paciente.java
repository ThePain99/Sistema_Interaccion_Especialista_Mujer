package com.interaction.interactionsystemwoman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="paciente")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    @Id
    @SequenceGenerator(
            name = "paciente_sequence",
            sequenceName = "paciente_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "paciente_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "nombre",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String nombre;

    @Column(
            name = "apellido",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String apellido;

    @Column(
            name = "dni",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String dni;

    @Column(
            name = "correo",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String correo;

    @Column(
            name = "numero",
            nullable = false
    )
    private Integer numero;

    @ManyToOne
    @JoinColumn(
            name = "id_Usuario",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="usuario_fk"
            )
    )
    /*@Column(
            name = "idPaciente",
            nullable = false
    )*/
    private Usuario usuario;

    @OneToMany(
            mappedBy = "paciente",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Consulta> consultas =new ArrayList<>();
}
