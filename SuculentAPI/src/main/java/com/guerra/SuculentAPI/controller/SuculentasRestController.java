package com.guerra.SuculentAPI.controller;

import com.guerra.SuculentAPI.model.dto.EtiquetaImagenDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@Log4j2
public class SuculentasRestController {

    @PostMapping("/registrar-suculenta")
    @ResponseBody
    public ResponseEntity<String> registrarSuculenta(
            @ModelAttribute EtiquetaImagenDto etiqueta,
            @RequestParam("fotos") List<MultipartFile> fotos
    ) {

        log.info("Etiqueta: " + etiqueta);
        log.info("Fotos: " + Arrays.toString(fotos.stream().map(MultipartFile::getOriginalFilename).toArray()));

        return ResponseEntity.ok().build();
    }
}
