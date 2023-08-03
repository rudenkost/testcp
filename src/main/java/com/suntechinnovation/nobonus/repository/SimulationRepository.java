package com.suntechinnovation.nobonus.repository;

import com.suntechinnovation.nobonus.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimulationRepository extends JpaRepository<Simulation, Long> {
  Optional<Simulation> findById(Long id);
}
