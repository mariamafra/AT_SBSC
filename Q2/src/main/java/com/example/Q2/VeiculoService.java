package com.example.Q2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    VeiculoRepository veiculoRepository;

    public List<Veiculo> getAllVeiculos() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> getVeiculoById(Long id) {
        return veiculoRepository.findById(id);
    }

    public Veiculo createVeiculo(Veiculo vehicle) {
        return veiculoRepository.save(vehicle);
    }

    public Optional<Veiculo> updateVeiculo(Long id, Veiculo vehicleDetails) {
        return veiculoRepository.findById(id).map(vehicle -> {
            vehicle.setMarca(vehicleDetails.getMarca());
            vehicle.setModelo(vehicleDetails.getModelo());
            vehicle.setAno(vehicleDetails.getAno());
            vehicle.setCor(vehicleDetails.getCor());
            return veiculoRepository.save(vehicle);
        });
    }

    public boolean deleteVeiculo(Long id) {
        return veiculoRepository.findById(id).map(vehicle -> {
            veiculoRepository.delete(vehicle);
            return true;
        }).orElse(false);
    }
}
