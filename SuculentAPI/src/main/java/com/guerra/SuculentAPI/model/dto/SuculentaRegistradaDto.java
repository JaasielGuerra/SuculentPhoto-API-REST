package com.guerra.SuculentAPI.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuculentaRegistradaDto implements Serializable {

    private List<String> imagenes;
    private String etiqueta;
    private String directorio;
}
