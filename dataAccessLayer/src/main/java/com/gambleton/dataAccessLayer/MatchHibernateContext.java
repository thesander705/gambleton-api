package com.gambleton.dataAccessLayer;

import com.gambleton.dataAccessLayer.abstraction.MatchContext;
import com.gambleton.models.Match;

import java.util.List;

public class MatchHibernateContext implements MatchContext {
    @Override
    public void create(Match entity) {
        
    }

    @Override
    public Match get(int id) {
        return null;
    }

    @Override
    public List<Match> getAll() {
        return null;
    }

    @Override
    public void update(Match entity) {

    }

    @Override
    public void delete(int id) {

    }
}
