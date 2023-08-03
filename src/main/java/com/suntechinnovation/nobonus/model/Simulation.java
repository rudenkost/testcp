package com.suntechinnovation.nobonus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Simulation {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String name;

  private Double firstDeposit;

  private Double bonus;

  private Double referralBonus;

  private Integer numberOfReferees;

  private Double turnoverMultiplier;

  private Double movePercentage;

  private Double odds;

  private Boolean newBonusModel;

  private Boolean useRefereesForTurnover;

  private Double goldenProportion;

  private Integer stake;

  private Double winner;

  private Long winnerBets;

  private Double averageWithdrawalBalance;

  private Double acquisitionToRetentionCost;

  private Double acquisitionToRetentionCostPercentage;

  private Double referralCost;
}
