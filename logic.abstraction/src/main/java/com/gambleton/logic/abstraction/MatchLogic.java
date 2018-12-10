package com.gambleton.logic.abstraction;

import com.gambleton.models.BetOption;
import com.gambleton.models.Match;

import java.util.List;

public interface MatchLogic {
    void createMatch(String title, String description, List<BetOption> betOptions);

    List<Match> getAllMatches();
}
