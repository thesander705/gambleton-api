package com.gambleton.logic.abstraction;

import com.gambleton.models.BetOption;
import com.gambleton.models.Game;
import com.gambleton.models.Match;

import java.util.Date;
import java.util.List;

public interface MatchLogic {
    void createMatch(String title, String description, List<BetOption> betOptions, Game game, Date startDate, Date endDate);

    List<Match> getAllMatches();

    List<Match> getMatchesByGame(int gameId);

    Match getMatch(int matchId);
}
