package com.guerra.SuculentAPI.controller;

import com.guerra.SuculentAPI.model.query.ConsultaSintomasFotos;
import com.guerra.SuculentAPI.repository.SintomaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final SintomaRepository sintomaRepository;

    public DashboardController(SintomaRepository sintomaRepository) {
        this.sintomaRepository = sintomaRepository;
    }

    @GetMapping
    public String dashboard(Model model) {

        List<ConsultaSintomasFotos> consultaSintomasFotos = sintomaRepository.consultarSintomasFotos()
                .stream()
                .collect(Collectors.groupingBy(
                        item -> item.getSintoma().equals("Cochinilla") || item.getSintoma().equals("Insectos") ? "Cochinilla / Insectos"
                                : item.getSintoma().equals("Hongo") || item.getSintoma().equals("Moho") ? "Hongo / Moho" : item.getSintoma(),
                        Collectors.summingInt(ConsultaSintomasFotos::getCantidadFotos)
                ))
                .entrySet()
                .stream()
                .map(item -> new ConsultaSintomasFotos() {
                    @Override
                    public String getSintoma() {
                        return item.getKey();
                    }

                    @Override
                    public int getCantidadFotos() {
                        return item.getValue();
                    }
                })
                .collect(Collectors.toList());

        int sumaTotalFotos = consultaSintomasFotos.stream()
                .mapToInt(ConsultaSintomasFotos::getCantidadFotos)
                .sum();

        consultaSintomasFotos.add(new ConsultaSintomasFotos() {
            @Override
            public String getSintoma() {
                return "TOTAL FOTOS";
            }

            @Override
            public int getCantidadFotos() {
                return sumaTotalFotos;
            }
        });


        model.addAttribute("sintomas", consultaSintomasFotos);

        return "index";
    }
}
