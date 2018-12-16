package com.gambleton.logic;

import com.gambleton.logic.abstraction.GameLogic;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.GameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class GameDefaultLogicTest {
    @Test
    public void createCheckIfGameGetsCreated() {
        GameRepository GameRepository = mock(GameRepository.class);

        String name = "TestGame";
        String description = "TestGameDescription";

        ArgumentCaptor<Game> argument = ArgumentCaptor.forClass(Game.class);

        GameLogic gameDefaultLogic = new GameDefaultLogic(GameRepository);
        gameDefaultLogic.CreateGame(name, description);

        verify(GameRepository).create(argument.capture());

        Assert.assertNotNull(argument.getValue());
    }

    @Test
    public void getAllReturnsAllGames() {
        GameRepository gameRepository = mock(GameRepository.class);

        Game gameFromContext1 = new Game();
        gameFromContext1.setId(1);
        gameFromContext1.setName("Test game");
        gameFromContext1.setDescription("testGame");

        Game gameFromContext2 = new Game();
        gameFromContext2.setId(2);
        gameFromContext2.setName("Test game");
        gameFromContext2.setDescription("testGame");

        ArrayList<Game> allGames = new ArrayList<Game>();
        allGames.add(gameFromContext1);
        allGames.add(gameFromContext2);

        when(gameRepository.getAll()).thenReturn(allGames);

        GameLogic gameLogic = new GameDefaultLogic(gameRepository);
        List<Game> allGamesToTest = gameLogic.GetAllGames();
        Assert.assertEquals(2, allGamesToTest.size());
    }

    @Test
    public void getReturnsGameWhenCorrectId() {
        GameRepository gameRepository = mock(GameRepository.class);
        int id = 1;

        Game gameFromRepository = new Game();
        gameFromRepository.setId(id);
        gameFromRepository.setName("Test game");
        gameFromRepository.setDescription("testGame");

        when(gameRepository.get(id)).thenReturn(gameFromRepository);

        GameLogic gameLogic = new GameDefaultLogic(gameRepository);
        Game game = gameLogic.getGame(id);
        assertNotNull(game);
    }
}
