package com.guerra.SuculentAPI.model.dto;

import com.guerra.SuculentAPI.model.query.ConsultaBasicaSintoma;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SintomaBasicoDto implements Serializable {

    private String idSintoma;
    private String sintoma;
    private String descripcion;

    public static SintomaBasicoDto mapeoBasicoDesde(ConsultaBasicaSintoma consultaBasicaSintoma) {
        return SintomaBasicoDto.builder()
                .idSintoma(consultaBasicaSintoma.getIdSintoma())
                .sintoma(consultaBasicaSintoma.getSintoma())
                .descripcion(consultaBasicaSintoma.getDescripcion())
                .build();
    }
}
