package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.CompetitorContext;
import com.gambleton.dataAccessLayer.abstraction.MatchContext;
import com.gambleton.models.Match;
import com.gambleton.repository.abstraction.MatchRepository;

import java.util.List;

public class MatchDefaultRepository implements MatchRepository {
    private MatchContext matchContext;

    public MatchDefaultRepository(MatchContext matchContext) {
        this.matchContext = matchContext;
    }

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
