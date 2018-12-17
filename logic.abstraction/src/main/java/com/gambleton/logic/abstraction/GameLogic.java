package com.gambleton.logic.abstraction;

import com.gambleton.models.Game;

import java.util.List;

public interface GameLogic {
    void CreateGame(String name, String description);

    List<Game> GetAllGames();

    Game getGame(int gameId);
}