package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.CompetitorContext;
import com.gambleton.models.Competitor;
import com.gambleton.repository.abstraction.CompetitorRepository;

import java.util.ArrayList;
import java.util.List;

public class CompetitorDefaultRepository implements CompetitorRepository {
    private CompetitorContext competitorContext;

    public CompetitorDefaultRepository(CompetitorContext competitorContext) {
        this.competitorContext = competitorContext;
    }

    @Override
    public void create(Competitor entity) {
        this.competitorContext.create(entity);
    }

    @Override
    public Competitor get(int id) {
        return this.competitorContext.get(id);
    }

    @Override
    public List<Competitor> getAll() {
        List<Competitor> competitors = this.competitorContext.getAll();
        if (competitors == null){
            competitors = new ArrayList<>();
        }
        return competitors;
    }

    @Override
    public void update(Competitor entity) {
        this.competitorContext.update(entity);
    }

    @Override
    public void delete(int id) {
        this.competitorContext.delete(id);
    }

    @Override
    public List<Competitor> getCompetitorsByGame(int gameId) {
        return this.competitorContext.getCompetitorsByGame(gameId);
    }
}
