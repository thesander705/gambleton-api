package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.CompetitorContext;
import com.gambleton.models.Competitor;
import com.gambleton.repository.abstraction.CompetitorRepository;

import java.util.List;

public class CompetitorDefaultRepository implements CompetitorRepository {
    private CompetitorContext competitorContext;

    public CompetitorDefaultRepository(CompetitorContext competitorContext) {
        this.competitorContext = competitorContext;
    }

    @Override
    public void create(Competitor entity) {

    }

    @Override
    public Competitor get(int id) {
        return null;
    }

    @Override
    public List<Competitor> getAll() {
        return null;
    }

    @Override
    public void update(Competitor entity) {

    }

    @Override
    public void delete(int id) {

    }
}
