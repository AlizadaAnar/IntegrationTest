package com.example.airplainproje;


import com.example.airplainproje.entity.Airplane;
import com.example.airplainproje.repository.AirplaneRepository;
import com.example.airplainproje.service.AirplaneServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AirplaneServiceImplIntegrationTest {

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private AirplaneServiceImpl airplaneService;

    private Airplane airplane1;

    @BeforeEach
    void setUp() {
        airplaneRepository.deleteAll();

        airplane1 = new Airplane();
        airplane1.setModel("Boeing 747");
        airplane1.setManufacturer("Boeing");
        airplane1.setCapacity(400);
        airplane1.setRangeKm(100);

        airplane1 = airplaneRepository.save(airplane1);
    }

    @AfterEach
    void cleanUp() {
        airplaneRepository.deleteAll();
    }

    @Test
    void getAllAirplanes() {
        List<Airplane> result = airplaneService.getAllAirplanes();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getModel()).isEqualTo("Boeing 747");
    }

    @Test
    void getAirplaneById_found() {
        Optional<Airplane> result = airplaneService.getAirplaneById(airplane1.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getModel()).isEqualTo("Boeing 747");
    }

    @Test
    void getAirplaneById_notFound() {
        Optional<Airplane> result = airplaneService.getAirplaneById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void createAirplane() {
        Airplane newAirplane = new Airplane();
        newAirplane.setModel("Airbus A320");
        newAirplane.setManufacturer("Airbus");
        newAirplane.setCapacity(180);
        newAirplane.setRangeKm(100);

        Airplane result = airplaneService.createAirplane(newAirplane);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getModel()).isEqualTo("Airbus A320");
    }

    @Test
    void updateAirplane_found() {
        Airplane updatedAirplane = new Airplane();
        updatedAirplane.setModel("Updated Model");
        updatedAirplane.setManufacturer("Updated Manufacturer");
        updatedAirplane.setCapacity(500);
        updatedAirplane.setRangeKm(100);

        Optional<Airplane> result = airplaneService.updateAirplane(airplane1.getId(), updatedAirplane);

        assertThat(result).isPresent();
        assertThat(result.get().getModel()).isEqualTo("Updated Model");
        assertThat(result.get().getCapacity()).isEqualTo(500);
        assertThat(result.get().getManufacturer()).isEqualTo(" nvfjkld");
    }

    @Test
    void updateAirplane_notFound() {
        Airplane updatedAirplane = new Airplane();
        updatedAirplane.setModel("Updated Model");

        Optional<Airplane> result = airplaneService.updateAirplane(999L, updatedAirplane);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteAirplane_found() {
        boolean deleted = airplaneService.deleteAirplane(airplane1.getId());
        assertThat(deleted).isTrue();
        assertThat(airplaneRepository.findById(airplane1.getId())).isEmpty();
    }

    @Test
    void deleteAirplane_notFound() {
        boolean deleted = airplaneService.deleteAirplane(999L);
        assertThat(deleted).isFalse();
    }

    @Test
    void findByManufacturer() {

    }

    @Test
    void searchByModel() {

    }

    @Test
    void findByMinimumCapacity() {

    }
}
