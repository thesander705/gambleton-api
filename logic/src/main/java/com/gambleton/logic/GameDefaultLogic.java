package com.gambleton.logic;

import com.gambleton.logic.abstraction.GameLogic;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.GameRepository;

public class GameDefaultLogic implements GameLogic {
    private GameRepository gameRepository;

    public GameDefaultLogic(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void CreateGame(Game game) {
        this.gameRepository.create(game);
    }
}
