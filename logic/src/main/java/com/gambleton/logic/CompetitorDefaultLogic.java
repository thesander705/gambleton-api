package com.gambleton.logic;

import com.gambleton.logic.abstraction.CompetitorLogic;
import com.gambleton.models.Competitor;
import com.gambleton.repository.abstraction.CompetitorRepository;

import java.util.List;

public class CompetitorDefaultLogic implements CompetitorLogic {
    private CompetitorRepository competitorRepository;

    public CompetitorDefaultLogic(CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }

    @Override
    public void createCompetitor(String name, String description, int gameId) {

    }

    @Override
    public List<Competitor> getAllCompetitors() {
        return null;
    }
}
