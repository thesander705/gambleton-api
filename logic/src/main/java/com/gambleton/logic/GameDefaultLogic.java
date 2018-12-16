package com.gambleton.logic;

import com.gambleton.logic.abstraction.GameLogic;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.GameRepository;

import java.util.List;

public class GameDefaultLogic implements GameLogic {
    private GameRepository gameRepository;

    public GameDefaultLogic(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void CreateGame(String name, String description) {
        Game game = new Game();
        game.setName(name);
        game.setDescription(description);
        gameRepository.create(game);
    }

    @Override
    public List<Game> GetAllGames() {
        return this.gameRepository.getAll();
    }

    @Override
    public Game getGame(int gameId) {
        return this.gameRepository.get(gameId);
    }
}
