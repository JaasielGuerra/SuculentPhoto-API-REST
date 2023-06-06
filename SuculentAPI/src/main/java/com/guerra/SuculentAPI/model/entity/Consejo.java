package com.guerra.SuculentAPI.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "consejo")
public class Consejo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "descripcion", nullable = false, length = 150)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "id_sintoma", nullable = false)
    private Sintoma idSintoma;

}
