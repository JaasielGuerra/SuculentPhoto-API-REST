package com.guerra.SuculentAPI.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EtiquetaImagenDto implements Serializable {

    private String sintoma;
    private String consejo;
}
