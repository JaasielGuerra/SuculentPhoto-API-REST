package com.guerra.SuculentAPI.repository;

import com.guerra.SuculentAPI.model.entity.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SintomaRepository extends JpaRepository<Sintoma, String> {

    @Modifying
    @Query(value = "UPDATE Sintoma s SET s.cantidadConsejos = s.cantidadConsejos + ?2, s.cantidadFotos = s.cantidadFotos + ?3 WHERE s.idSintoma = ?1")
    void aumentarCantidadConsejosYFotos(String idSintoma, int cantidadConsejos, int cantidadFotos);

    @Query(value = "SELECT s.cantidadFotos FROM Sintoma s WHERE s.idSintoma = ?1")
    int findCantidadFotosByIdSintoma(String idSintoma);
}