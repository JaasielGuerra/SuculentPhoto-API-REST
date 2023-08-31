package com.guerra.SuculentAPI.repository;

import com.guerra.SuculentAPI.model.entity.Sintoma;
import com.guerra.SuculentAPI.model.query.ConsultaBasicaSintoma;
import com.guerra.SuculentAPI.model.query.ConsultaSintomasFotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SintomaRepository extends JpaRepository<Sintoma, String> {

    @Modifying
    @Query(value = "UPDATE Sintoma s SET s.cantidadConsejos = s.cantidadConsejos + ?2, s.cantidadFotos = s.cantidadFotos + ?3 WHERE s.idSintoma = ?1")
    void aumentarCantidadConsejosYFotos(String idSintoma, int cantidadConsejos, int cantidadFotos);

    @Query(value = "SELECT s.cantidadFotos FROM Sintoma s WHERE s.idSintoma = ?1")
    int findCantidadFotosByIdSintoma(String idSintoma);

    @Query(value = "SELECT s.idSintoma AS idSintoma, s.sintoma AS sintoma, s.descripcion AS descripcion FROM Sintoma s WHERE not s.idSintoma = 'SALUDABLE'")
    List<ConsultaBasicaSintoma> consultarSintomas();

    @Query(value = "SELECT s.sintoma AS sintoma, s.cantidadFotos AS cantidadFotos FROM Sintoma s")
    List<ConsultaSintomasFotos> consultarSintomasFotos();

}