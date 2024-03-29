package com.guerra.SuculentAPI.service;

import com.guerra.SuculentAPI.exception.SuculentException;
import com.guerra.SuculentAPI.model.dto.EtiquetaImagenDto;
import com.guerra.SuculentAPI.model.dto.SintomaBasicoDto;
import com.guerra.SuculentAPI.model.dto.SintomaDto;
import com.guerra.SuculentAPI.model.dto.SuculentaRegistradaDto;
import com.guerra.SuculentAPI.model.entity.Consejo;
import com.guerra.SuculentAPI.model.entity.Sintoma;
import com.guerra.SuculentAPI.model.query.ConsultaBasicaSintoma;
import com.guerra.SuculentAPI.repository.ConsejoRepository;
import com.guerra.SuculentAPI.repository.SintomaRepository;
import lombok.extern.java.Log;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class RegistroSuculentasServiceImpl implements RegistroSuculentasService {

    @Value("${suculent.photo.upload-dir}")
    private String dirImagenes;
    private List<String> fotosGuardadas;
    private String rutaDirectorioImagen;

    private final SintomaRepository sintomaRepository;
    private final ConsejoRepository consejoRepository;

    private final String SINTOMA_SALUDABLE = "SALUDABLE";

    public RegistroSuculentasServiceImpl(SintomaRepository sintomaRepository, ConsejoRepository consejoRepository) {
        this.sintomaRepository = sintomaRepository;
        this.consejoRepository = consejoRepository;
    }

    @Override
    @Transactional
    public SuculentaRegistradaDto registrarSuculenta(List<MultipartFile> fotos, EtiquetaImagenDto etiqueta) throws SuculentException, IOException,
            DataAccessException, ConstraintViolationException, TransactionException {

        SuculentException exceptionAcumulador = new SuculentException();

        //TODO: validar ortografia
        if (fotos.get(0).isEmpty()) {
            exceptionAcumulador.addException(new SuculentException("No se han enviado fotos"));
        }

        /*   
        int MAX_FOTOS = 6;
        if (!fotos.get(0).isEmpty() && fotos.size() != MAX_FOTOS) {
            exceptionAcumulador.addException(new SuculentException("Deben enviarse " + MAX_FOTOS + " fotos, se enviaron " + fotos.size()));
        }
        */

        //validar que sean imagenes
        for (MultipartFile foto : fotos) {
            log.info("Validando foto " + foto.getOriginalFilename() + " con tipo " + foto.getContentType());
            if (foto.getContentType() != null && !foto.getContentType().startsWith("image/")) {
                log.info("La foto " + foto.getOriginalFilename() + " no es una imagen");
                exceptionAcumulador.addException(new SuculentException("El archivo " + foto.getOriginalFilename() + " no es una imagen"));
            }
        }

        if (etiqueta.getIdSintoma() == null || etiqueta.getIdSintoma().isEmpty()) {
            exceptionAcumulador.addException(new SuculentException("El campo idSintoma no puede estar vacío"));
        }

        /*
        if ((etiqueta.getConsejo() == null || etiqueta.getConsejo().isEmpty()) && !etiqueta.getIdSintoma().equals(SINTOMA_SALUDABLE)) {
            exceptionAcumulador.addException(new SuculentException("El campo consejo no puede estar vacío"));
        }
        */

        if(!etiqueta.getIdSintoma().equals(SINTOMA_SALUDABLE) && etiqueta.getConsejo() != null && etiqueta.getConsejo().length() > 150){
            exceptionAcumulador.addException(new SuculentException("El consejo no puede tener más de 150 letras"));
        }

        if (etiqueta.getIdSintoma() != null && !sintomaRepository.existsById(etiqueta.getIdSintoma())) {
            exceptionAcumulador.addException(new SuculentException("El idSintoma " + etiqueta.getIdSintoma() + " no existe, por favor registre el sintoma primero"));
        }

        // si hay excepciones, las lanzo
        if (exceptionAcumulador.hasExceptions()) {
            throw exceptionAcumulador.build();
        }

        guardarFotos(etiqueta, fotos);

        persistirDatos(etiqueta, fotos.size());

        return SuculentaRegistradaDto.builder()
                .imagenes(fotosGuardadas)
                .etiqueta(etiqueta.getIdSintoma())
                .directorio(rutaDirectorioImagen)
                .build();
    }

    private void persistirDatos(EtiquetaImagenDto etiqueta, int cantFotos) {

        int cantidadConsejos = 1;

        if(etiqueta.getIdSintoma().equals(SINTOMA_SALUDABLE)){
            cantidadConsejos = 0;
        }

        if(etiqueta.getConsejo() == null || etiqueta.getConsejo().isEmpty()){
            cantidadConsejos = 0;
        }

        sintomaRepository.aumentarCantidadConsejosYFotos(etiqueta.getIdSintoma(), cantidadConsejos, cantFotos);

        // si es sintoma saludable, no se registran consejos
        if (etiqueta.getIdSintoma().equals(SINTOMA_SALUDABLE)) {
            return;
        }

        //si el consejo viene null o vacio, no se registra nada
        if(etiqueta.getConsejo() == null || etiqueta.getConsejo().isEmpty()){
            return;
        }

        Consejo consejo = new Consejo();
        consejo.setDescripcion(etiqueta.getConsejo().trim());
        consejo.setIdSintoma(sintomaRepository.getReferenceById(etiqueta.getIdSintoma()));
        consejoRepository.save(consejo);
    }

    private void guardarFotos(EtiquetaImagenDto etiqueta, List<MultipartFile> fotos) throws IOException {

        int cantFotos = sintomaRepository.findCantidadFotosByIdSintoma(etiqueta.getIdSintoma());

        rutaDirectorioImagen = dirImagenes + File.separator + etiqueta.getIdSintoma();
        Path nuevoDirectorio = Path.of(rutaDirectorioImagen);

        log.info("Creando directorio: " + nuevoDirectorio);
        Files.createDirectories(nuevoDirectorio);

        log.info("Guardando fotos en: " + nuevoDirectorio);
        fotosGuardadas = new ArrayList<>();
        for (MultipartFile foto : fotos) {

            String nombreImagen = "IMAGE_" + (++cantFotos) + ".jpg";
            String rutaFoto = rutaDirectorioImagen + File.separator + nombreImagen;
            Path nuevaFoto = Path.of(rutaFoto);

            log.info("Guardando foto: " + nuevaFoto);
            Files.copy(foto.getInputStream(), nuevaFoto);
            fotosGuardadas.add(nombreImagen);
        }

    }

    @Override
    public SintomaDto registrarSintoma(SintomaDto sintomaDto) throws SuculentException, DataAccessException,
            ConstraintViolationException, TransactionException {

        SuculentException exceptionAcumulador = new SuculentException();

        if (sintomaDto.getSintoma() == null || sintomaDto.getSintoma().isEmpty()) {
            exceptionAcumulador.addException(new SuculentException("El campo sintoma no puede estar vacío"));
        }

        if (sintomaDto.getDescripcion() == null || sintomaDto.getDescripcion().isEmpty()) {
            exceptionAcumulador.addException(new SuculentException("El campo descripción no puede estar vacío"));
        }

        if(sintomaDto.getSintoma().length() >50){
            exceptionAcumulador.addException(new SuculentException("El sintoma no puede tener más de 50 letras"));
        }

        if(sintomaDto.getDescripcion().length() > 150){
            exceptionAcumulador.addException(new SuculentException("La descripción no puede tener más de 150 letras"));
        }

        // si hay excepciones, las lanzo
        if (exceptionAcumulador.hasExceptions()) {
            throw exceptionAcumulador.build();
        }

        String sintomaNormalizado = normalizarTexto(sintomaDto.getSintoma());
        String idSintomaConstruido = sintomaNormalizado
                .trim()
                .toUpperCase()
                .replaceAll(" ", "_");

        Sintoma sintoma = new Sintoma();
        sintoma.setIdSintoma(idSintomaConstruido);
        sintoma.setSintoma(sintomaDto.getSintoma().trim());
        sintoma.setDescripcion(sintomaDto.getDescripcion().trim());
        sintoma.setCantidadConsejos(0);
        sintoma.setCantidadFotos(0);

        Sintoma sintomaRegistrado = sintomaRepository
                .findById(idSintomaConstruido) //busco el sintoma por id
                .orElse(sintomaRepository.save(sintoma)); //si no existe, lo guardo

        return SintomaDto.builder()
                .idSintoma(sintomaRegistrado.getIdSintoma())
                .sintoma(sintomaRegistrado.getSintoma())
                .descripcion(sintomaRegistrado.getDescripcion())
                .cantidadConsejos(sintomaRegistrado.getCantidadConsejos())
                .cantidadFotos(sintomaRegistrado.getCantidadFotos())
                .build();
    }

    private String normalizarTexto(String texto) {
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        textoNormalizado = textoNormalizado.replaceAll("\\p{M}", "");
        return textoNormalizado;
    }

    @Override
    public List<SintomaBasicoDto> obtenerSintomas() {

        List<ConsultaBasicaSintoma> sintomas = sintomaRepository.consultarSintomas();
        return sintomas.stream()
                .map(SintomaBasicoDto::mapeoBasicoDesde)
                .collect(Collectors.toList());
    }
}
