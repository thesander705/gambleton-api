package com.gambleton.presentation.viewModels.userController;

public class PlaceBets {
    private int userPlacingBetId;
    private int betOptionId;
    private double amountOfMoney;

    public int getUserPlacingBetId() {
        return userPlacingBetId;
    }

    public void setUserPlacingBetId(int userPlacingBetId) {
        this.userPlacingBetId = userPlacingBetId;
    }

    public int getBetOptionId() {
        return betOptionId;
    }

    public void setBetOptionId(int betOptionId) {
        this.betOptionId = betOptionId;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }
}
