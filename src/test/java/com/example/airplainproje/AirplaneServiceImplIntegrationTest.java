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



    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void cleanUp() {
    }

    @Test
    void getAllAirplanes() {

    }

    @Test
    void getAirplaneById_found() {
    }

    @Test
    void getAirplaneById_notFound() {
    }

    @Test
    void createAirplane() {

    }

    @Test
    void updateAirplane_found() {

    }

    @Test
    void updateAirplane_notFound() {

    }

    @Test
    void deleteAirplane_found() {

    }

    @Test
    void deleteAirplane_notFound() {

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
