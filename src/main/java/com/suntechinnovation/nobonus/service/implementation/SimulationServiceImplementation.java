package com.suntechinnovation.nobonus.service.implementation;

import com.suntechinnovation.nobonus.model.Simulation;
import com.suntechinnovation.nobonus.model.SimulationInputParameters;
import com.suntechinnovation.nobonus.repository.SimulationRepository;
import com.suntechinnovation.nobonus.service.BonusSimulator;
import com.suntechinnovation.nobonus.service.model.SimulationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class SimulationServiceImplementation implements SimulationService {
    private final SimulationRepository simulationRepository;

    @Override
    public Simulation create(SimulationInputParameters simulationInputParameters) {
        log.info("Creating new simulation");

        return simulationRepository.save(new Simulation());
    }

    @Override
    public Collection<Simulation> list(int limit) {
        log.info("Fetching all simulations");

        return simulationRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Optional<Simulation> get(Long id) {
        log.info("Fetching simulation by id");

        return simulationRepository.findById(id);
    }

    public Simulation simulate(SimulationInputParameters parameters) {
        Simulation simulation = new BonusSimulator(
          parameters.getName(),
          parameters.getNewBonusModel(),
          parameters.getMovePercentage(),
          parameters.getTurnoverMultiplier(),
          parameters.getOdds(),
          parameters.getUseRefereesForTurnover(),
          parameters.getGoldenProportion(),
          parameters.getGreedFactor(),
          parameters.getSmartWithdrawal(),
          parameters.getMinWithdrawalAmount()
        ).simulate(
          parameters.getFirstDeposit(),
          parameters.getBonus(),
          parameters.getReferralBonus(),
          parameters.getNumberOfReferees(),
          parameters.getStake(),
          parameters.getRenewalDeposit(),
          parameters.getRenewalBonus()
        );
        simulationRepository.save(simulation);
        return simulation;
    }
}
