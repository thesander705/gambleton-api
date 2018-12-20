package com.gambleton.logic.abstraction;

import com.gambleton.models.Competitor;

import java.util.List;

public interface CompetitorLogic {
    void createCompetitor(String name, String description, int gameId);
    List<Competitor> getAllCompetitors();
    List<Competitor> getCompetitorsByGame(int gameId);
}
