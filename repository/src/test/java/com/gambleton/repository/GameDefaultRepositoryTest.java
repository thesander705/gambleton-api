package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.GameContext;
import com.gambleton.dataAccessLayer.abstraction.UserContext;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.GameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
}
