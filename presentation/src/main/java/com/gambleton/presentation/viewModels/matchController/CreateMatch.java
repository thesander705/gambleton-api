package com.gambleton.presentation.viewModels.matchController;

import com.gambleton.presentation.viewModels.matchController.createMatch.BetOption;

import java.util.List;

public class CreateMatch {
    private String title;
    private String description;
    private int gameId;
    private List<BetOption> betOptions;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public List<BetOption> getBetOptions() {
        return betOptions;
    }

    public void setBetOptions(List<BetOption> betOptions) {
        this.betOptions = betOptions;
    }
}
