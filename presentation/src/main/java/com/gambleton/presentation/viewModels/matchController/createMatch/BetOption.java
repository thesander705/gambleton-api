package com.gambleton.presentation.viewModels.matchController.createMatch;

public class BetOption {
    private int competitorId;
    private double payoutRate;

    public int getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(int competitorId) {
        this.competitorId = competitorId;
    }

    public double getPayoutRate() {
        return payoutRate;
    }

    public void setPayoutRate(double payoutRate) {
        this.payoutRate = payoutRate;
    }
}
