package com.gambleton.logic;

import com.gambleton.logic.abstraction.MatchLogic;
import com.gambleton.models.BetOption;
import com.gambleton.models.Game;
import com.gambleton.models.Match;
import com.gambleton.repository.abstraction.MatchRepository;

import java.util.Date;
import java.util.List;

public class MatchDefaultLogic implements MatchLogic {
    private MatchRepository matchRepository;

    public MatchDefaultLogic(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public void createMatch(String title, String description, List<BetOption> betOptions, Game game, Date startDate, Date endDate) {
        Match match = new Match();
        match.setTitle(title);
        match.setDescription(description);
        match.setBetOptions(betOptions);
        match.setGame(game);
        match.setStartDate(startDate);
        match.setEndDate(endDate);
        this.matchRepository.create(match);
    }

    @Override
    public List<Match> getAllMatches() {
        return this.matchRepository.getAll();
    }

    @Override
    public List<Match> getMatchesByGame(int gameId) {
        return this.matchRepository.getMatchesByGame(gameId);
    }

    @Override
    public Match getMatch(int matchId) {
        return this.matchRepository.get(matchId);
    }
}
