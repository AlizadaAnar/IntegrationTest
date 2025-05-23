package com.example.airplainproje.controller;

import com.example.airplainproje.entity.Airplane;
import com.example.airplainproje.service.AirplaneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;

    @Autowired
    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @GetMapping
    public ResponseEntity<List<Airplane>> getAllAirplanes() {
        return ResponseEntity.ok(airplaneService.getAllAirplanes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airplane> getAirplaneById(@PathVariable Long id) {
        return airplaneService.getAirplaneById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Airplane> createAirplane(@RequestBody Airplane airplane) {
        Airplane created = airplaneService.createAirplane(airplane);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airplane> updateAirplane(@PathVariable Long id, @RequestBody Airplane airplane) {
        return airplaneService.updateAirplane(id, airplane)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirplane(@PathVariable Long id) {
        boolean deleted = airplaneService.deleteAirplane(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/manufacturer/{manufacturer}")
    public ResponseEntity<List<Airplane>> getByManufacturer(@PathVariable String manufacturer) {
        List<Airplane> airplanes = airplaneService.findByManufacturer(manufacturer);
        return airplanes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(airplanes);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Airplane>> searchByModel(@RequestParam String model) {
        List<Airplane> airplanes = airplaneService.searchByModel(model);
        return airplanes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(airplanes);
    }

    @GetMapping("/capacity/{minCapacity}")
    public ResponseEntity<List<Airplane>> getByMinCapacity(@PathVariable Integer minCapacity) {
        List<Airplane> airplanes = airplaneService.findByMinimumCapacity(minCapacity);
        return airplanes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(airplanes);
    }
}
