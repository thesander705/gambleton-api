package com.gambleton.repository.abstraction;

import com.gambleton.models.Match;

import java.util.List;

public interface MatchRepository extends Repository<Match> {
    List<Match> getMatchesByGame(int gameId);
}
