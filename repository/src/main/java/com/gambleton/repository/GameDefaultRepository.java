package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.GameContext;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.GameRepository;

import java.util.ArrayList;
import java.util.List;

public class GameDefaultRepository implements GameRepository {
    private GameContext gameContext;

    public GameDefaultRepository(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void create(Game entity) {
        gameContext.create(entity);
    }

    @Override
    public Game get(int id) {
        return gameContext.get(id);
    }

    @Override
    public List<Game> getAll() {
        List<Game> games =  gameContext.getAll();

        if (games == null){
            games = new ArrayList<Game>();
        }

        return games;
    }

    @Override
    public void update(Game entity) {
        gameContext.update(entity);
    }

    @Override
    public void delete(int id) {
        gameContext.delete(id);
    }
}
