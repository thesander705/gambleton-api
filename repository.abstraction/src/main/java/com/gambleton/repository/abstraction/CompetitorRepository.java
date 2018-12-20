package com.gambleton.repository.abstraction;

import com.gambleton.models.Competitor;

import java.util.List;

public interface CompetitorRepository extends Repository<Competitor> {
    List<Competitor> getCompetitorsByGame(int gameId);
}
