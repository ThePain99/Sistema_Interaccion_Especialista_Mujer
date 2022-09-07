package com.interaction.interactionsystemwoman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="usuario")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @SequenceGenerator(
            name = "usuario_sequence",
            sequenceName = "usuario_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usuario_sequence"
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
            name = "contrasena",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String contrasena;

    @Column(
            name = "estado",
            nullable = false
    )
    private Boolean estado;

    @Column(
            name = "tipo",
            nullable = false
    )
    private Boolean tipo;

    @ManyToOne
    @JoinColumn(
            name = "id_Modalidad",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="modalidad_usuario_fk"
            )
    )
    /*@Column(
            name = "idModalidad",
            nullable = false
    )*/
    private Modalidad modalidad;

    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Consulta> consultas =new ArrayList<>();
}
