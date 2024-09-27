package com.example.AT_SBSC.Q7;

import com.example.AT_SBSC.Q7.controller.ClimaController;
import com.example.AT_SBSC.Q7.model.Clima;
import com.example.AT_SBSC.Q7.service.ClimaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ClimaController.class)
public class ClimaControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ClimaService climaService;

    private Clima clima;

    @Test
    public void testGetClima() {
        String country = "BR";
        String token = "da781857638c7d8638d6315e8acdc9b0";
        String mockResponse = "[{\"country\":\"BR\",\"date\":\"2024-03-31\",\"text\":\"A Zona de Convergência Intertropical (ZCIT) está ativa e espalha nuvens carregadas pelo RN e o norte do CE. Uma nova frente fria já começa a influenciar o tempo no RS. No começo da tarde deste domingo (31), pancadas de chuva eram observadas na fronteira com o Uruguai. Até \u00e0 noite, as pancadas de chuva se espalham por mais \u00e1reas do estado. Uma \u00e1rea de baixa press\u00e3o no Paraguai, associado a um cavado nos níveis médios da atmosfera, estimula a formação de nuvens carregadas em MS. \"}]";

        when(climaService.getClima(country, token))
                .thenReturn(Mono.just(List.of(
                        new Clima("BR", "2024-03-31", "A Zona de Convergência Intertropical (ZCIT) está ativa e espalha nuvens carregadas pelo RN e o norte do CE. Uma nova frente fria já começa a influenciar o tempo no RS. No começo da tarde deste domingo (31), pancadas de chuva eram observadas na fronteira com o Uruguai. Até \u00e0 noite, as pancadas de chuva se espalham por mais \u00e1reas do estado. Uma \u00e1rea de baixa press\u00e3o no Paraguai, associado a um cavado nos níveis médios da atmosfera, estimula a formação de nuvens carregadas em MS. ")
                )));

        webTestClient.get()
                .uri("/weather?country=BR&token=da781857638c7d8638d6315e8acdc9b0")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Clima.class)
                .hasSize(1)
                .contains(new Clima("BR", "Previsão para 2024-03-31", "A Zona de Convergência Intertropical (Zona de Convergência Intertropical (ZCIT)) está ativa e espalha nuvens carregadas pelo RN e o norte do CE. Uma nova frente fria já começa a influenciar o tempo no RS. No começo da tarde deste domingo (31), pancadas de chuva eram observadas na fronteira com o Uruguai. Até à noite, as pancadas de chuva se espalham por mais áreas do estado. Uma área de baixa pressão no Paraguai, associado a um área de baixa pressão atmosférica nos níveis médios da atmosfera, estimula a formação de nuvens carregadas em MS. "));
    }
}
