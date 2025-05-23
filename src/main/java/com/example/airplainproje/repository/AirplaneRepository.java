package com.example.airplainproje.repository;


import com.example.airplainproje.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    List<Airplane> findByManufacturer(String manufacturer);
    List<Airplane> findByModelContainingIgnoreCase(String modelKeyword);
    List<Airplane> findByCapacityGreaterThanEqual(Integer capacity);
}
