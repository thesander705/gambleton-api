package com.gambleton.logic;

import com.gambleton.logic.abstraction.CompetitorLogic;
import com.gambleton.models.Competitor;
import com.gambleton.repository.abstraction.CompetitorRepository;
import com.gambleton.repository.abstraction.GameRepository;

import java.util.List;

public class CompetitorDefaultLogic implements CompetitorLogic {
    private CompetitorRepository competitorRepository;
    private GameRepository gameRepository;

    public CompetitorDefaultLogic(CompetitorRepository competitorRepository, GameRepository gameRepository) {
        this.competitorRepository = competitorRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void createCompetitor(String name, String description, int gameId) {
        Competitor competitor = new Competitor();
        competitor.setName(name);
        competitor.setDescription(description);
        competitor.setGame(gameRepository.get(gameId));
        this.competitorRepository.create(competitor);
    }

    @Override
    public List<Competitor> getAllCompetitors() {
        return this.competitorRepository.getAll();
    }

    @Override
    public List<Competitor> getCompetitorsByGame(int gameId) {
        return this.competitorRepository.getCompetitorsByGame(gameId);
    }
}
