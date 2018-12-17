package com.gambleton.dataAccessLayer.abstraction;

import com.gambleton.models.Match;

import java.util.List;

public interface MatchContext extends Context<Match> {
    List<Match> getMatchesByGame(int gameId);
}
