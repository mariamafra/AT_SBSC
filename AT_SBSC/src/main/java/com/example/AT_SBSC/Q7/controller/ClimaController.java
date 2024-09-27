package com.example.AT_SBSC.Q7.controller;

import com.example.AT_SBSC.Q7.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import com.example.AT_SBSC.Q7.model.Clima;

import java.util.List;

@RestController
public class ClimaController {
    @Autowired
    private ClimaService climaService;

    @GetMapping("/weather")
    public Mono<List<Clima>> getClima(@RequestParam String country, @RequestParam String token) {
        // Token = da781857638c7d8638d6315e8acdc9b0/ Country BR
        return climaService.getClima(country, token)
                .map(climaList -> {
                    return climaList.stream()
                            .map(clima -> {
                                String dataFormatada = "Previsão para " + clima.getDate();
                                String textoProcessado = clima.getText()
                                        .replace("ZCIT", "Zona de Convergência Intertropical (ZCIT)")
                                        .replace("cavado", "área de baixa pressão atmosférica");

                                clima.setDate(dataFormatada);
                                clima.setText(textoProcessado);
                                return clima;
                            })
                            .toList();
                });
    }
}
