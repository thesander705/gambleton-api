package com.gambleton.logic;

import com.gambleton.logic.abstraction.MatchLogic;
import com.gambleton.models.BetOption;
import com.gambleton.models.Match;
import com.gambleton.repository.abstraction.MatchRepository;

import java.util.List;

public class MatchDefaultLogic implements MatchLogic {
    private MatchRepository matchRepository;

    public MatchDefaultLogic(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public void createMatch(String title, String description, List<BetOption> betOptions) {
        Match match = new Match();
        match.setTitle(title);
        match.setDescription(description);
        match.setBetOptions(betOptions);
        this.matchRepository.create(match);
    }

    @Override
    public List<Match> getAllMatches() {
        return this.matchRepository.getAll();
    }
}
