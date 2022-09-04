package com.interaction.interactionsystemwoman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="violencia")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Violencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "violencia",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String violencia;

    @OneToMany(
            mappedBy = "violencia",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<ViolenciaConsulta> violenciaConsultas =new ArrayList<>();
}
