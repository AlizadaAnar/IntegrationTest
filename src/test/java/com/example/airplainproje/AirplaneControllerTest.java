package com.example.airplainproje;


import com.example.airplainproje.controller.AirplaneController;
import com.example.airplainproje.entity.Airplane;
import com.example.airplainproje.service.AirplaneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AirplaneController.class)
class AirplaneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirplaneService airplaneService;

    @Autowired
    private ObjectMapper objectMapper;

    private Airplane airplane1;

    @BeforeEach
    void setUp() {
        airplane1 = new Airplane();
        airplane1.setId(1L);
        airplane1.setModel("Boeing 747");
        airplane1.setManufacturer("Boeing");
        airplane1.setCapacity(400);
    }

    @Test
    void getAllAirplanes() throws Exception {
        when(airplaneService.getAllAirplanes()).thenReturn(List.of(airplane1));

        mockMvc.perform(get("/api/airplanes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].model").value("Boeing 747"));
    }

    @Test
    void getAirplaneById_found() throws Exception {
        when(airplaneService.getAirplaneById(1L)).thenReturn(Optional.of(airplane1));

        mockMvc.perform(get("/api/airplanes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getAirplaneById_notFound() throws Exception {
        when(airplaneService.getAirplaneById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/airplanes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAirplane() throws Exception {
        when(airplaneService.createAirplane(any(Airplane.class))).thenReturn(airplane1);

        mockMvc.perform(post("/api/airplanes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airplane1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }


    @Test
    void updateAirplane_found() throws Exception {
        when(airplaneService.updateAirplane(eq(1L), any(Airplane.class))).thenReturn(Optional.of(airplane1));

        mockMvc.perform(put("/api/airplanes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airplane1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateAirplane_notFound() throws Exception {
        when(airplaneService.updateAirplane(eq(999L), any(Airplane.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/airplanes/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airplane1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAirplane_found() throws Exception {
        when(airplaneService.deleteAirplane(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/airplanes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAirplane_notFound() throws Exception {
        when(airplaneService.deleteAirplane(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/airplanes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByManufacturer_found() throws Exception {
        when(airplaneService.findByManufacturer("Boeing")).thenReturn(List.of(airplane1));

        mockMvc.perform(get("/api/airplanes/manufacturer/Boeing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].manufacturer").value("Boeing"));
    }

    @Test
    void getByManufacturer_noContent() throws Exception {
        when(airplaneService.findByManufacturer("Unknown")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/airplanes/manufacturer/Unknown"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchByModel_found() throws Exception {
        when(airplaneService.searchByModel("Boe")).thenReturn(List.of(airplane1));

        mockMvc.perform(get("/api/airplanes/search").param("model", "Boe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("Boeing 747"));
    }

    @Test
    void searchByModel_noContent() throws Exception {
        when(airplaneService.searchByModel("XYZ")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/airplanes/search").param("model", "XYZ"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByMinimumCapacity_found() throws Exception {
        when(airplaneService.findByMinimumCapacity(300)).thenReturn(List.of(airplane1));

        mockMvc.perform(get("/api/airplanes/capacity/300"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].capacity").value(400));
    }

    @Test
    void findByMinimumCapacity_noContent() throws Exception {
        when(airplaneService.findByMinimumCapacity(1000)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/airplanes/capacity/1000"))
                .andExpect(status().isNoContent());
    }
}
