package com.gambleton.dataAccessLayer;

import com.gambleton.dataAccessLayer.abstraction.CompetitorContext;
import com.gambleton.models.Competitor;

import java.util.List;

public class CompetitorHibernateContext implements CompetitorContext {
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
