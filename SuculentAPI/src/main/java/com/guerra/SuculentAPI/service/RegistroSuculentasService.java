package com.guerra.SuculentAPI.service;


import com.guerra.SuculentAPI.exception.SuculentException;
import com.guerra.SuculentAPI.model.dto.EtiquetaImagenDto;
import com.guerra.SuculentAPI.model.dto.SintomaDto;
import com.guerra.SuculentAPI.model.dto.SuculentaRegistradaDto;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RegistroSuculentasService {

        @Transactional
        SuculentaRegistradaDto registrarSuculenta(List<MultipartFile> fotos, EtiquetaImagenDto etiqueta)
                throws SuculentException, IOException, DataAccessException, ConstraintViolationException, TransactionException;

        @Transactional
        SintomaDto registrarSintoma(SintomaDto sintomaDto) throws SuculentException, DataAccessException, ConstraintViolationException, TransactionException;
}
