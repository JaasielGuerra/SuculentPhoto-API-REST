package com.guerra.SuculentAPI.controller;

import com.guerra.SuculentAPI.model.query.ConsultaSintomasFotos;
import com.guerra.SuculentAPI.repository.SintomaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final SintomaRepository sintomaRepository;

    public DashboardController(SintomaRepository sintomaRepository) {
        this.sintomaRepository = sintomaRepository;
    }

    @GetMapping
    public String dashboard(Model model) {

        List<ConsultaSintomasFotos> consultaSintomasFotos = sintomaRepository.consultarSintomasFotos();
        model.addAttribute("sintomas", consultaSintomasFotos);

        return "index";
    }
}
