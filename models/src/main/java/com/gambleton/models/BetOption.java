package com.gambleton.models;

public class BetOption {
    private int id;
    private Competitor competitor;
    private double payoutRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public double getPayoutRate() {
        return payoutRate;
    }

    public void setPayoutRate(double payoutRate) {
        this.payoutRate = payoutRate;
    }
}
