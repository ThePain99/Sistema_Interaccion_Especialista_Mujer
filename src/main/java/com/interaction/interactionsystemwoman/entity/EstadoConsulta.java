package com.interaction.interactionsystemwoman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="estadoconsulta")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoConsulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "estado",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String estado;

    @OneToMany(
            mappedBy = "estadoConsulta",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Consulta> consultas =new ArrayList<>();
}
