package com.guerra.SuculentAPI.repository;

import com.guerra.SuculentAPI.model.entity.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SintomaRepository extends JpaRepository<Sintoma, String> {
}