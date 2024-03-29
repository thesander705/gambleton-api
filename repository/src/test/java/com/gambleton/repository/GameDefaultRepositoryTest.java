package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.GameContext;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.GameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class GameDefaultRepositoryTest {
    @Test
    public void createCheckIfGameGetsCreated() {
        GameContext gameContext = mock(GameContext.class);

        Game gameToCreate = new Game();
        gameToCreate.setName("Test game");
        gameToCreate.setDescription("testGame");

        ArgumentCaptor<Game> argument = ArgumentCaptor.forClass(Game.class);

        GameRepository gameRepository = new GameDefaultRepository(gameContext);
        gameRepository.create(gameToCreate);

        verify(gameContext).create(argument.capture());

        Assert.assertNotNull(argument.getValue());
    }

    @Test
    public void getReturnsGameWhenCorrectId() {
        GameContext gameContext = mock(GameContext.class);
        int id = 1;

        Game gameFromContext = new Game();
        gameFromContext.setId(id);
        gameFromContext.setName("Test game");
        gameFromContext.setDescription("testGame");

        when(gameContext.get(id)).thenReturn(gameFromContext);

        GameRepository gameRepository = new GameDefaultRepository(gameContext);
        Game gameFromRepository = gameRepository.get(id);
        assertNotNull(gameFromRepository);
    }


    @Test
    public void getAllReturnsAllGames() {
        GameContext GameContext = mock(GameContext.class);

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

        when(GameContext.getAll()).thenReturn(allGames);

        GameRepository gameRepository = new GameDefaultRepository(GameContext);
        List<Game> allGamesToTest = gameRepository.getAll();
        Assert.assertEquals(2, allGamesToTest.size());
    }

    @Test
    public void getAllReturnsEmptyListWhenDataIsNull() {
        GameContext gameContext = mock(GameContext.class);

        when(gameContext.getAll()).thenReturn(null);

        GameRepository gameRepository = new GameDefaultRepository(gameContext);
        List<Game> allGamesToTest = gameRepository.getAll();
        Assert.assertNotNull(allGamesToTest);
    }

    @Test
    public void updateUpdatesAGame() {
        GameContext gameContext = mock(GameContext.class);

        Game gameToUpdate = new Game();
        gameToUpdate.setId(1);
        gameToUpdate.setName("Test game");
        gameToUpdate.setDescription("testGame");


        ArgumentCaptor<Game> argument = ArgumentCaptor.forClass(Game.class);
        doNothing().when(gameContext).update(any(Game.class));

        GameRepository gameRepository = new GameDefaultRepository(gameContext);
        gameRepository.update(gameToUpdate);
        verify(gameContext).update(argument.capture());
        Assert.assertEquals(argument.getValue().getName(), gameToUpdate.getName());
        Assert.assertEquals(argument.getValue().getDescription(), gameToUpdate.getDescription());
        Assert.assertEquals(argument.getValue().getId(), gameToUpdate.getId());
    }

    @Test
    public void deleteDeletesAGame() {
        GameContext gameContext = mock(GameContext.class);

        int gameId = 1;

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(int.class);
        doNothing().when(gameContext).delete(anyInt());

        GameRepository gameRepository = new GameDefaultRepository(gameContext);
        gameRepository.delete(gameId);
        verify(gameContext).delete(argument.capture());
        Assert.assertEquals(argument.getValue().intValue(), gameId);
    }
}
