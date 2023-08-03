package com.suntechinnovation.nobonus.controller;

import com.suntechinnovation.nobonus.model.Simulation;
import com.suntechinnovation.nobonus.model.SimulationInputParameters;
import com.suntechinnovation.nobonus.service.implementation.SimulationServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/simulation")
@RequiredArgsConstructor
public class SimulationController {
    private final SimulationServiceImplementation simulationService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Simulation> getSimulation(@PathVariable("id") Long id) {
        return ResponseEntity.ok(simulationService.get(id).orElseThrow(() -> new RuntimeException("No entry found")));
    }

    @GetMapping("/list")
    public ResponseEntity<Collection<Simulation>> getSimulations() {
        return ResponseEntity.ok(simulationService.list(100));
    }

    @PostMapping("/simulate")
    public ResponseEntity<Simulation> simulate(
            @RequestBody
                    SimulationInputParameters simulationInputParameters
    ) {
        return ResponseEntity.ok(simulationService.simulate(simulationInputParameters));
    }
}
