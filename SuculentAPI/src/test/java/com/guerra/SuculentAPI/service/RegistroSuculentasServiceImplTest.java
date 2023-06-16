package com.guerra.SuculentAPI.service;

import com.guerra.SuculentAPI.exception.SuculentException;
import com.guerra.SuculentAPI.model.dto.SintomaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RegistroSuculentasServiceImplTest {

    private final RegistroSuculentasService registroSuculentasService;

    @Autowired
    public RegistroSuculentasServiceImplTest(RegistroSuculentasService registroSuculentasService) {
        this.registroSuculentasService = registroSuculentasService;
    }

    @Test
    void registrarSuculenta() {
        SintomaDto sintoma = SintomaDto.builder()
                .sintoma("RESEQUEDAD")
                .descripcion("La planta se ve seca")
                .build();

        try {
            registroSuculentasService.registrarSintoma(sintoma);
        } catch (SuculentException e) {
            e.printStackTrace();
        }
    }

    @Test
    void obtenerSintomas() {

        List<SintomaDto> sintomaDtos = registroSuculentasService.obtenerSintomas();

        for (SintomaDto sintomaDto : sintomaDtos) {
            System.out.println(sintomaDto);
        }

    }
}