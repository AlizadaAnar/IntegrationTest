package com.example.airplainproje.service;



import com.example.airplainproje.entity.Airplane;

import java.util.List;
import java.util.Optional;

public interface AirplaneService {
    List<Airplane> getAllAirplanes();

    Optional<Airplane> getAirplaneById(Long id);

    Airplane createAirplane(Airplane airplane);

    Optional<Airplane> updateAirplane(Long id, Airplane airplane);

    boolean deleteAirplane(Long id);

    List<Airplane> findByManufacturer(String manufacturer);

    List<Airplane> searchByModel(String modelKeyword);

    List<Airplane> findByMinimumCapacity(Integer capacity);
}
