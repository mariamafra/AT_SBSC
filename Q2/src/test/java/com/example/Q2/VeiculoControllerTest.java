package com.example.Q2;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(VeiculoController.class)
public class VeiculoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    private Veiculo v1;
    private Veiculo v2;

    @BeforeEach
    public void setup() {
        v1 = new Veiculo();
        v1.setId(1L);
        v1.setMarca("Ford");
        v1.setModelo("Fiesta");
        v1.setAno(2018);
        v1.setCor("Azul");

        v2 = new Veiculo();
        v2.setId(2L);
        v2.setMarca("Chevrolet");
        v2.setModelo("Onix");
        v2.setAno(2019);
        v2.setCor("Vermelho");
    }

    @Test
    public void testGetAllVeiculos() throws Exception {
        given(vehicleService.getAllVeiculos()).willReturn(Arrays.asList(v1, v2));

        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].marca").value("Ford"))
                .andExpect(jsonPath("$[1].marca").value("Chevrolet"));
    }

    @Test
    public void testGetVeiculoById_Found() throws Exception {
        given(vehicleService.getVeiculoById(1L)).willReturn(Optional.of(v1));

        mockMvc.perform(get("/veiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Ford"))
                .andExpect(jsonPath("$.modelo").value("Fiesta"));
    }

    @Test
    public void testGetVeiculoById_NotFound() throws Exception {
        given(vehicleService.getVeiculoById(3L)).willReturn(Optional.empty());

        mockMvc.perform(get("/veiculos/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVeiculo() throws Exception {
        Veiculo newVeiculo = new Veiculo();
        newVeiculo.setMarca("Toyota");
        newVeiculo.setModelo("Corolla");
        newVeiculo.setAno(2020);
        newVeiculo.setCor("Prata");

        Veiculo savedVeiculo = new Veiculo();
        savedVeiculo.setId(3L);
        savedVeiculo.setMarca("Toyota");
        savedVeiculo.setModelo("Corolla");
        savedVeiculo.setAno(2020);
        savedVeiculo.setCor("Prata");

        given(vehicleService.createVeiculo(ArgumentMatchers.any(Veiculo.class))).willReturn(savedVeiculo);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newVeiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.marca").value("Toyota"));
    }

    @Test
    public void testUpdateVeiculo_Found() throws Exception {
        Veiculo updatedDetails = new Veiculo();
        updatedDetails.setMarca("Honda");
        updatedDetails.setModelo("Civic");
        updatedDetails.setAno(2021);
        updatedDetails.setCor("Preto");

        Veiculo updatedVeiculo = new Veiculo();
        updatedVeiculo.setId(1L);
        updatedVeiculo.setMarca("Honda");
        updatedVeiculo.setModelo("Civic");
        updatedVeiculo.setAno(2021);
        updatedVeiculo.setCor("Preto");

        given(vehicleService.updateVeiculo(1L, updatedDetails)).willReturn(Optional.of(updatedVeiculo));

        mockMvc.perform(put("/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Honda"))
                .andExpect(jsonPath("$.modelo").value("Civic"));
    }

    @Test
    public void testUpdateVeiculo_NotFound() throws Exception {
        Veiculo updatedDetails = new Veiculo();
        updatedDetails.setMarca("Honda");
        updatedDetails.setModelo("Civic");
        updatedDetails.setAno(2021);
        updatedDetails.setCor("Preto");

        given(vehicleService.updateVeiculo(3L, updatedDetails)).willReturn(Optional.empty());

        mockMvc.perform(put("/veiculos/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteVeiculo_Found() throws Exception {
        given(vehicleService.deleteVeiculo(1L)).willReturn(true);

        mockMvc.perform(delete("/veiculos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteVeiculo_NotFound() throws Exception {
        given(vehicleService.deleteVeiculo(3L)).willReturn(false);

        mockMvc.perform(delete("/veiculos/3"))
                .andExpect(status().isNotFound());
    }
}
