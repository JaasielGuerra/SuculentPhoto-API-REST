package com.guerra.SuculentAPI.service;

import com.guerra.SuculentAPI.exception.SuculentException;
import com.guerra.SuculentAPI.model.dto.EtiquetaImagenDto;
import com.guerra.SuculentAPI.model.dto.SuculentaRegistradaDto;
import com.guerra.SuculentAPI.model.entity.Consejo;
import com.guerra.SuculentAPI.repository.ConsejoRepository;
import com.guerra.SuculentAPI.repository.SintomaRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class RegistroSuculentasServiceImpl implements RegistroSuculentasService {

    @Value("${suculent.photo.upload-dir}")
    private String dirImagenes;
    private List<String> fotosGuardadas;
    private String rutaDirectorioImagen;

    private final SintomaRepository sintomaRepository;
    private final ConsejoRepository consejoRepository;

    public RegistroSuculentasServiceImpl(SintomaRepository sintomaRepository, ConsejoRepository consejoRepository) {
        this.sintomaRepository = sintomaRepository;
        this.consejoRepository = consejoRepository;
    }

    @Override
    @Transactional
    public SuculentaRegistradaDto registrarSuculenta(List<MultipartFile> fotos, EtiquetaImagenDto etiqueta) throws SuculentException, IOException {

        SuculentException exceptionAcumulador = new SuculentException();

        //TODO: validar ortografia
        if (fotos.get(0).isEmpty()) {
            exceptionAcumulador.addException(new SuculentException("No se han enviado fotos"));
        }

        int MAX_FOTOS = 6;
        if (!fotos.get(0).isEmpty() && fotos.size() != MAX_FOTOS) {
            exceptionAcumulador.addException(new SuculentException("Deben enviarse " + MAX_FOTOS + " fotos, se enviaron " + fotos.size()));
        }

        //validar que sean imagenes
        for (MultipartFile foto : fotos) {
            if (foto.getContentType() != null && !foto.getContentType().startsWith("image/")) {
                exceptionAcumulador.addException(new SuculentException("El archivo " + foto.getOriginalFilename() + " no es una imagen"));
            }
        }

        if (etiqueta.getIdSintoma() == null || etiqueta.getIdSintoma().isEmpty()) {
            exceptionAcumulador.addException(new SuculentException("El campo idSintoma no puede estar vacío"));
        }

        if (etiqueta.getConsejo() == null || etiqueta.getConsejo().isEmpty()) {
            exceptionAcumulador.addException(new SuculentException("El campo consejo no puede estar vacío"));
        }

        if (etiqueta.getIdSintoma() != null && !sintomaRepository.existsById(etiqueta.getIdSintoma())) {
            exceptionAcumulador.addException(new SuculentException("El idSintoma " + etiqueta.getIdSintoma() + " no existe, por favor registre el sintoma primero"));
        }

        // si hay excepciones, las lanzo
        if (exceptionAcumulador.hasExceptions()) {
            throw exceptionAcumulador.build();
        }


        persistirDatos(etiqueta);

        guardarFotos(etiqueta, fotos);

        return SuculentaRegistradaDto.builder()
                .imagenes(fotosGuardadas)
                .etiqueta(etiqueta.getIdSintoma())
                .directorio(rutaDirectorioImagen)
                .build();
    }

    private void persistirDatos(EtiquetaImagenDto etiqueta) {

        //TODO: actualizar la cantidad de fotos en la tabla sintoma
        //TODO: actualizar la cantidad de consejos en la tabla sintoma
        Consejo consejo = new Consejo();
        consejo.setDescripcion(etiqueta.getConsejo());
        consejo.setIdSintoma(sintomaRepository.getReferenceById(etiqueta.getIdSintoma()));
        consejoRepository.save(consejo);
    }

    private void guardarFotos(EtiquetaImagenDto etiqueta, List<MultipartFile> fotos) throws IOException {

        //guardar fotos en disco
        //TODO: colocar el numero de la foto en el nombre, asi IMAGE_1.jpg, IMAGE_2.jpg, etc
        rutaDirectorioImagen = dirImagenes + File.separator + etiqueta.getIdSintoma();
        Path nuevoDirectorio = Path.of(rutaDirectorioImagen);

        log.info("Creando directorio: " + nuevoDirectorio);
        Files.createDirectories(nuevoDirectorio);

        log.info("Guardando fotos en: " + nuevoDirectorio);
        fotosGuardadas = new ArrayList<>();
        for (MultipartFile foto : fotos) {

            String rutaFoto = rutaDirectorioImagen + File.separator + foto.getOriginalFilename();
            Path nuevaFoto = Path.of(rutaFoto);

            log.info("Guardando foto: " + nuevaFoto);
            Files.copy(foto.getInputStream(), nuevaFoto);
            fotosGuardadas.add(foto.getOriginalFilename());
        }

    }
}
