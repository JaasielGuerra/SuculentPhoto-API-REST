package com.guerra.SuculentAPI.controller;

import com.guerra.SuculentAPI.exception.SuculentException;
import com.guerra.SuculentAPI.model.dto.EtiquetaImagenDto;
import com.guerra.SuculentAPI.model.dto.ResponseDataDto;
import com.guerra.SuculentAPI.model.dto.SintomaDto;
import com.guerra.SuculentAPI.model.dto.SuculentaRegistradaDto;
import com.guerra.SuculentAPI.service.RegistroSuculentasService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class SuculentasRestController {

    private final RegistroSuculentasService registroSuculentasService;

    public SuculentasRestController(RegistroSuculentasService registroSuculentasService) {
        this.registroSuculentasService = registroSuculentasService;
    }

    @PostMapping("/sintoma")
    @ResponseBody
    public ResponseEntity<ResponseDataDto> registrarSintoma(@RequestBody SintomaDto sintomaDto){

        ResponseDataDto respuestaRegistroSintoma = new ResponseDataDto();

        try {

            SintomaDto sintomaRegistrado = registroSuculentasService.registrarSintoma(sintomaDto);

            respuestaRegistroSintoma = ResponseDataDto.builder()
                    .message("Sintoma registrado exitosamente")
                    .data(sintomaRegistrado)
                    .errors(Collections.emptyList())
                    .build();

        } catch (SuculentException e) {

            log.error(e.getMessage());

            List<String> errores = e.getExceptionsList()
                    .stream()
                    .map(SuculentException::getMessage)
                    .collect(Collectors.toList());

            respuestaRegistroSintoma = ResponseDataDto.builder()
                    .message("Error al registrar sintoma")
                    .errors(errores)
                    .build();

            return ResponseEntity.badRequest().body(respuestaRegistroSintoma);

        } catch (Exception e) {

            log.error(e.getMessage());

            respuestaRegistroSintoma = ResponseDataDto.builder()
                    .message(e.toString())
                    .errors(Collections.singletonList(e.getMessage()))
                    .build();

            return ResponseEntity.internalServerError().body(respuestaRegistroSintoma);

        }

        return ResponseEntity.ok(respuestaRegistroSintoma);
    }

    @PostMapping("/registrar-suculenta")
    @ResponseBody
    public ResponseEntity<ResponseDataDto> registrarSuculenta(
            @ModelAttribute EtiquetaImagenDto etiqueta,
            @RequestParam("fotos") List<MultipartFile> fotos
    ) {

        ResponseDataDto respuestaRegistroSuculenta = new ResponseDataDto();

        try {

            SuculentaRegistradaDto suculentaRegistradaDto = registroSuculentasService.registrarSuculenta(fotos, etiqueta);

            respuestaRegistroSuculenta = ResponseDataDto.builder()
                    .message("Suculenta registrada exitosamente")
                    .data(suculentaRegistradaDto)
                    .errors(Collections.emptyList())
                    .build();

        } catch (SuculentException e) {// status 400

            log.error(e.getMessage());

            List<String> errores = e.getExceptionsList()
                    .stream()
                    .map(SuculentException::getMessage)
                    .collect(Collectors.toList());

            respuestaRegistroSuculenta = ResponseDataDto.builder()
                    .message("Error al registrar suculenta")
                    .errors(errores)
                    .build();

            return ResponseEntity.badRequest().body(respuestaRegistroSuculenta);


        } catch (Exception e) { // status 500

            log.error(e.getMessage(), e);

            respuestaRegistroSuculenta = ResponseDataDto.builder()
                    .message(e.toString())
                    .errors(Collections.singletonList(e.getMessage()))
                    .build();

            return ResponseEntity.internalServerError().body(respuestaRegistroSuculenta);
        }

        return ResponseEntity.ok(respuestaRegistroSuculenta);
    }
}
