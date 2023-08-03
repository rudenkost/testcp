package com.suntechinnovation.nobonus.service;

import com.suntechinnovation.nobonus.model.Simulation;

import java.util.Random;

public class BonusSimulator {
    private final String name;
    private final boolean referreesForTurnover;
    private final double greedFactor;
    private double goldenStandard;
    Random random = new Random();
    private static final double stopThreshold = 10;
    private static final int simulations = (int) 1e5;
    private static final boolean turnoverOnlyOnDeposit = true;

    private final boolean move;
    private final double percMove;
    private final double withdrawTurnoverCoeff;

    private final double odd;

    private final boolean smartWithdrawal;
    private final double minWithdrawAmount;

    public BonusSimulator(
        String name,
        boolean move,
        double percentMove,
        double withdrawTurnoverCoeff,
        double odd,
        boolean referreesForTurnover,
        double goldenStandard,
        double greedFactor,
        boolean smartWithdrawal,
        double minWithdrawAmount
    ) {
        this.name = name;
        this.move = move;
        this.percMove = percentMove;
        this.withdrawTurnoverCoeff = withdrawTurnoverCoeff;
        this.odd = odd;
        this.referreesForTurnover = referreesForTurnover;
        this.goldenStandard = goldenStandard;
        this.greedFactor = greedFactor;
        this.smartWithdrawal = smartWithdrawal;
        this.minWithdrawAmount = minWithdrawAmount;
    }

    public Simulation simulate(double firstDep, double bonus, double referalBonus, int numberOfReferrees, double stake,
                               double renewDeposit, double renewalBonus) {
        System.out.println("______________");
        double withdrawableBalance = 0;
        int totalSteps = 0;
        int winners = 0;
        double initialMoney = firstDep + renewDeposit;

        for (int i=0;i<simulations;++i) {
            double money = firstDep + renewDeposit;
            double bonusMoney = bonus + renewalBonus + referalBonus * numberOfReferrees;
            double turnover = 0;

            double neededTurnover = (firstDep + renewDeposit + (referreesForTurnover ? bonusMoney : (bonus + renewalBonus))) * withdrawTurnoverCoeff;

            if (turnoverOnlyOnDeposit) {
                neededTurnover = money * withdrawTurnoverCoeff;
            }
            int steps = 0;

            double maxWb = 0.;
            double bestProp = 1.;

            double withdrawnMoney = 0.;

            while ((move && (bonusMoney > stopThreshold || turnover < neededTurnover))
                    || (!move && turnover < neededTurnover && bonusMoney > stopThreshold)) {
                // golden standard

                if (turnover >= neededTurnover && money>=minWithdrawAmount) {
                    withdrawnMoney+=money;
                    money = 0;
                }

                double st = Math.min(stake, money + bonusMoney);

                double split = money / (money + bonusMoney);

                if (split >= goldenStandard && move && turnover>=neededTurnover) {
                    break;
                }

                // a person wins a lot and decides to withdraw
                if (!smartWithdrawal) {
                    // no need to stop with smart
                    if (turnover >= neededTurnover && getWithdrawableBalance(withdrawnMoney, money, bonusMoney) >= initialMoney * greedFactor) {
                        break;
                    }
                }

                // place bet
                ++steps;

                if (random.nextBoolean()) {
                    money+=odd*(st*split);
                    bonusMoney+=odd*(st*(1-split));
                } else {
                    money-=st*split;
                    bonusMoney-=st*(1-split);
                }

                if (move) {
                    double moveAmount = Math.min(st * percMove, bonusMoney);
                    money+=moveAmount;
                    bonusMoney-=moveAmount;
                }

                turnover+=st;
            }

            if (turnover >= neededTurnover) {
                double wb = getWithdrawableBalance(withdrawnMoney, money, bonusMoney);
                withdrawableBalance+=wb;

                if (wb >= firstDep) {
                    winners++;
                    totalSteps+=steps;
                }
            }
        }

        Simulation simulation = new Simulation();

        simulation.setName(name);
        simulation.setFirstDeposit(firstDep);
        simulation.setBonus(bonus);
        simulation.setNumberOfReferees(numberOfReferrees);
        simulation.setReferralBonus(referalBonus);
        simulation.setTurnoverMultiplier(this.withdrawTurnoverCoeff);
        simulation.setMovePercentage(this.percMove);
        simulation.setOdds(this.odd);
        simulation.setNewBonusModel(this.move);
        simulation.setUseRefereesForTurnover(this.referreesForTurnover);
        simulation.setGoldenProportion(this.goldenStandard);
        simulation.setStake((int) stake);

        simulation.setWinner(1.*winners / simulations);
        simulation.setWinnerBets((long) (1.*totalSteps / Math.max(winners, 1)));
        simulation.setAverageWithdrawalBalance(withdrawableBalance / simulations);
        simulation.setAcquisitionToRetentionCost(simulation.getAverageWithdrawalBalance() - initialMoney);
        simulation.setAcquisitionToRetentionCostPercentage(100.*simulation.getAcquisitionToRetentionCost()/initialMoney);

        simulation.setReferralCost(numberOfReferrees == 0 ? 0 : simulation.getAcquisitionToRetentionCost()/numberOfReferrees);

        return simulation;
    }

    private double getWithdrawableBalance(double withdrawmMoney, double money, double bonusMoney) {
        double wb = money + withdrawmMoney;
        if (!move) {
            wb+=bonusMoney;
        }
        return wb;
    }

    public static void main(String[] args) {
        Simulation result = new BonusSimulator("Massive", true, 0.04, 1, 0.90, false, 1., 5., true, 1000).simulate(1000, 2500, 500, 0, 200, 0, 0);
        System.out.println(result.toString());
    }
}
