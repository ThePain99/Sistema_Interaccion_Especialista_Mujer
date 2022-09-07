package com.interaction.interactionsystemwoman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="modalidad")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Modalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "modalidad",
            //nullable = false,
            columnDefinition = "TEXT"
    )
    private String modalidad;

    @OneToMany(
            mappedBy = "modalidad",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Usuario> usuarios =new ArrayList<>();

    @OneToMany(
            mappedBy = "modalidad",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Consulta> consultas =new ArrayList<>();
}
