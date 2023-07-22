package com.guerra.SuculentAPI.controller;

import com.guerra.SuculentAPI.exception.SuculentException;
import com.guerra.SuculentAPI.model.dto.*;
import com.guerra.SuculentAPI.service.RegistroSuculentasService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@Log4j2
public class SuculentasRestController {

    private final RegistroSuculentasService registroSuculentasService;
    private List<String> mensajesAgradecimientoList = new ArrayList<>();

    public SuculentasRestController(RegistroSuculentasService registroSuculentasService) {
        this.registroSuculentasService = registroSuculentasService;

        mensajesAgradecimientoList.add("Agradezco tu valiosa contribución. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Valoro mucho tu ayuda en mi investigación. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Tu participación es fundamental, gracias. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Estoy agradecido por tu colaboración. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Gracias por formar parte de mi estudio. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Tu aporte es invaluable, gracias. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Aprecio sinceramente tu participación. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Tu contribución es esencial para mi trabajo. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Estoy agradecido por contar contigo. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Valoro enormemente tu ayuda, gracias. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Agradezco tu valioso aporte a mi tesis. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Estoy muy agradecido por tu colaboración. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Gracias por contribuir a mi estudio. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Tu participación es muy apreciada, gracias. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Agradezco tu generosidad al compartir tus fotos. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Gracias por ser parte de mi proyecto de tesis. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Estoy agradecido por tu compromiso. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Tu ayuda es fundamental, gracias por colaborar. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Valoro tu tiempo y esfuerzo, gracias por participar. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Aprecio sinceramente tu disposición a colaborar. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Gracias por tu valiosa contribución. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Tu participación es muy valiosa, gracias. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Agradezco tu apoyo en mi investigación. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Estoy agradecido por tu compromiso. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Gracias por compartir tus fotos conmigo. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Tu aporte es muy significativo, gracias por colaborar. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Aprecio enormemente tu participación. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Estoy agradecido por formar parte de mi tesis. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Valoro sinceramente tu colaboración. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Estoy muy agradecido por contar contigo. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Gracias por contribuir con tu tiempo y fotos. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Tu participación es esencial, gracias. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Agradezco tu generosidad en compartir tus fotos. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Gracias por ser parte de mi investigación. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Estoy agradecido por tu valiosa contribución. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Tu participación es muy apreciada, gracias. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Aprecio sinceramente tu disposición a colaborar. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Gracias por tu valioso aporte. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Tu participación es esencial, gracias. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Valoro tu tiempo y esfuerzo, gracias por ayudar. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Agradezco sinceramente tu apoyo. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Estoy agradecido por tu compromiso. \uD83D\uDE0A");
        mensajesAgradecimientoList.add("Gracias por compartir tus fotos conmigo. \uD83D\uDC4F");
        mensajesAgradecimientoList.add("Tu aporte es de gran importancia, gracias por colaborar. \uD83D\uDE4F");
        mensajesAgradecimientoList.add("Aprecio enormemente tu participación. \uD83D\uDC4D");
        mensajesAgradecimientoList.add("Estoy agradecido por contar contigo. \uD83D\uDE0A");

    }

    @GetMapping("/sintomas")
    @ResponseBody
    public ResponseEntity<ResponseDataDto> consultarSintomasBasico() {

        ResponseDataDto respuestaConsultaSintomas = new ResponseDataDto();

        try {

            List<SintomaBasicoDto> sintomas = registroSuculentasService.obtenerSintomas();

            respuestaConsultaSintomas = ResponseDataDto.builder()
                    .message("Consulta basica de sintomas exitosa")
                    .data(sintomas)
                    .errors(Collections.emptyList())
                    .build();

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            respuestaConsultaSintomas = ResponseDataDto.builder()
                    .message(e.toString())
                    .errors(Collections.singletonList(e.getMessage()))
                    .build();

            return ResponseEntity.internalServerError().body(respuestaConsultaSintomas);

        }

        return ResponseEntity.ok(respuestaConsultaSintomas);
    }

    @PostMapping("/sintoma")
    @ResponseBody
    public ResponseEntity<ResponseDataDto> registrarSintoma(@RequestBody SintomaDto sintomaDto) {

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
                    .message(mensajesAgradecimientoList.get(obtenerNumeroAleatorio()))
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

    private int obtenerNumeroAleatorio(){
        int rango = 46; // Rango máximo del número aleatorio (0 - 45)
        Random random = new Random();
        return random.nextInt(rango);
    }
}
