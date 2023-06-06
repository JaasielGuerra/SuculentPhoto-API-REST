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

}
