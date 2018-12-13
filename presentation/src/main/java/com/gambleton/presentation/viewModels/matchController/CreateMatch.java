package com.gambleton.presentation.viewModels.matchController;

import com.gambleton.presentation.viewModels.matchController.createMatch.BetOption;

import java.util.Date;
import java.util.List;

public class CreateMatch {
    private String title;
    private String description;
    private int gameId;
    private List<BetOption> betOptions;
    private Date startDate;
    private Date endDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
