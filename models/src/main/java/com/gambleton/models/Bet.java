package com.gambleton.models;

public class Bet {
    private int id;
    private BetOption betOption;
    private double moneyPlaced;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BetOption getBetOption() {
        return betOption;
    }

    public void setBetOption(BetOption betOption) {
        this.betOption = betOption;
    }

    public double getMoneyPlaced() {
        return moneyPlaced;
    }

    public void setMoneyPlaced(double moneyPlaced) {
        this.moneyPlaced = moneyPlaced;
    }
}
