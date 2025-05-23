package com.example.airplainproje.service;


import com.example.airplainproje.entity.Airplane;
import com.example.airplainproje.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AirplaneServiceImpl implements AirplaneService {

    private final AirplaneRepository airplaneRepository;

    @Autowired
    public AirplaneServiceImpl(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    @Override
    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    @Override
    public Optional<Airplane> getAirplaneById(Long id) {
        return airplaneRepository.findById(id);
    }

    @Override
    public Airplane createAirplane(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    @Override
    public Optional<Airplane> updateAirplane(Long id, Airplane airplane) {
        return airplaneRepository.findById(id)
                .map(existing -> {
                    airplane.setId(id);
                    return airplaneRepository.save(airplane);
                });
    }

    @Override
    public boolean deleteAirplane(Long id) {
        return airplaneRepository.findById(id)
                .map(existing -> {
                    airplaneRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public List<Airplane> findByManufacturer(String manufacturer) {
        return airplaneRepository.findByManufacturer(manufacturer);
    }

    @Override
    public List<Airplane> searchByModel(String modelKeyword) {
        return airplaneRepository.findByModelContainingIgnoreCase(modelKeyword);
    }

    @Override
    public List<Airplane> findByMinimumCapacity(Integer capacity) {
        return airplaneRepository.findByCapacityGreaterThanEqual(capacity);
    }
}