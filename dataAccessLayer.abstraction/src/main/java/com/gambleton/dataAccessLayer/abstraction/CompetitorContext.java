package com.gambleton.dataAccessLayer.abstraction;

import com.gambleton.models.Competitor;

import java.util.List;

public interface CompetitorContext extends Context<Competitor> {
    List<Competitor> getCompetitorsByGame(int gameId);
}
