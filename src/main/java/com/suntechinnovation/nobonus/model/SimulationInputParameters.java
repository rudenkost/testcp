package com.suntechinnovation.nobonus.model;

import lombok.Data;

@Data
public class SimulationInputParameters {
    private String name;

    private Long firstDeposit;

    private Long bonus;

    private Long referralBonus;

    private Integer numberOfReferees;

    private Double turnoverMultiplier;

    private Boolean newBonusModel;

    private Double movePercentage;

    private Double odds;

    private Double goldenProportion;

    private Boolean useRefereesForTurnover = true;

    private Integer stake = 500;

    private Double greedFactor = 3.;

    private Double renewalDeposit = 0.;

    private Double renewalBonus = 0.;

    private Boolean smartWithdrawal = true;

    private Double minWithdrawalAmount = 500.;
}
