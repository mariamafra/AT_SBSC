package com.example.AT_SBSC.Q7.service;

import com.example.AT_SBSC.Q7.model.Clima;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClimaService {
    private final WebClient webClient;

    public ClimaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://apiadvisor.climatempo.com.br/api/v1/anl/synoptic/locale").build();
    }

    public Mono<String> getClimaJson(String country, String token) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{country}")
                        .queryParam("token", token)
                        .build(country))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<List<Clima>> getClima(String country, String token) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{country}")
                        .queryParam("token", token)
                        .build(country))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Clima>>() {});
    }
}
