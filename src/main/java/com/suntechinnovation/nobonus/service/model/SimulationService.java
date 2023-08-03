package com.suntechinnovation.nobonus.service.model;

import com.suntechinnovation.nobonus.model.Simulation;
import com.suntechinnovation.nobonus.model.SimulationInputParameters;

import java.util.Collection;
import java.util.Optional;

public interface SimulationService {
  Simulation create(SimulationInputParameters simulationInputParameters);
  Collection<Simulation> list(int limit);
  Optional<Simulation> get(Long id);
}
