package com.guerra.SuculentAPI.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "sintoma")
@Entity
public class Sintoma {

    @Id
    @Column(name = "id_sintoma", nullable = false, length = 50)
    private String idSintoma;

    @Column(name = "sintoma", nullable = false, length = 50)
    private String sintoma;

    @Column(name = "descripcion", nullable = false, length = 150)
    private String descripcion;

    @Column(name = "cantidad_consejos", nullable = false)
    private int cantidadConsejos;

    @Column(name = "cantidad_fotos", nullable = false)
    private int cantidadFotos;

}
