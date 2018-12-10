package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.CompetitorContext;
import com.gambleton.dataAccessLayer.abstraction.MatchContext;
import com.gambleton.models.Match;
import com.gambleton.repository.abstraction.MatchRepository;

import java.util.ArrayList;
import java.util.List;

public class MatchDefaultRepository implements MatchRepository {
    private MatchContext matchContext;

    public MatchDefaultRepository(MatchContext matchContext) {
        this.matchContext = matchContext;
    }

    @Override
    public void create(Match entity) {
        matchContext.create(entity);
    }

    @Override
    public Match get(int id) {
        return matchContext.get(id);
    }

    @Override
    public List<Match> getAll() {
        List<Match> matchs =  matchContext.getAll();

        if (matchs == null){
            matchs = new ArrayList<Match>();
        }

        return matchs;
    }

    @Override
    public void update(Match entity) {
        matchContext.update(entity);
    }

    @Override
    public void delete(int id) {
        matchContext.delete(id);
    }
}
