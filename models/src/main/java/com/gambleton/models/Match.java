package com.gambleton.models;

import java.util.List;

public class Match {
    private int id;
    private String title;
    private String description;
    private List<BetOption> betOptions;
    private Game game;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public List<BetOption> getBetOptions() {
        return betOptions;
    }

    public void setBetOptions(List<BetOption> betOptions) {
        this.betOptions = betOptions;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
