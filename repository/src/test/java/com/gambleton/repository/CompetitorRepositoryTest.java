package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.CompetitorContext;
import com.gambleton.models.Competitor;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.CompetitorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CompetitorRepositoryTest {
    @Test
    public void createCheckIfCompetitorGetsCreated() {
        CompetitorContext competitorContext = mock(CompetitorContext.class);

        Game game = new Game();
        game.setDescription("Test game");
        game.setName("Game");
        game.setId(1);

        Competitor competitorToCreate = new Competitor();
        competitorToCreate.setName("Test competitor");
        competitorToCreate.setDescription("testCompetitor");
        competitorToCreate.setGame(game);

        ArgumentCaptor<Competitor> argument = ArgumentCaptor.forClass(Competitor.class);

        CompetitorRepository competitorRepository = new CompetitorDefaultRepository(competitorContext);
        competitorRepository.create(competitorToCreate);

        verify(competitorContext).create(argument.capture());

        Assert.assertNotNull(argument.getValue());
    }

    @Test
    public void getReturnsCompetitorWhenCorrectId() {
        CompetitorContext competitorContext = mock(CompetitorContext.class);
        Game game = new Game();
        game.setDescription("Test game");
        game.setName("Game");
        game.setId(1);

        int id = 1;

        Competitor competitorFromContext = new Competitor();
        competitorFromContext.setId(id);
        competitorFromContext.setName("Test competitor");
        competitorFromContext.setDescription("testCompetitor");
        competitorFromContext.setGame(game);

        when(competitorContext.get(id)).thenReturn(competitorFromContext);

        CompetitorRepository competitorRepository = new CompetitorDefaultRepository(competitorContext);
        Competitor competitorFromRepository = competitorRepository.get(id);
        assertNotNull(competitorFromRepository);
    }


    @Test
    public void getAllReturnsAllCompetitors() {
        CompetitorContext competitorContext = mock(CompetitorContext.class);
        Game game = new Game();
        game.setDescription("Test game");
        game.setName("Game");
        game.setId(1);

        Competitor competitorFromContext1 = new Competitor();
        competitorFromContext1.setId(1);
        competitorFromContext1.setName("Test competitor");
        competitorFromContext1.setDescription("testCompetitor");
        competitorFromContext1.setGame(game);

        Competitor competitorFromContext2 = new Competitor();
        competitorFromContext2.setId(2);
        competitorFromContext2.setName("Test competitor");
        competitorFromContext2.setDescription("testCompetitor");
        competitorFromContext2.setGame(game);

        ArrayList<Competitor> allCompetitors = new ArrayList<Competitor>();
        allCompetitors.add(competitorFromContext1);
        allCompetitors.add(competitorFromContext2);

        when(competitorContext.getAll()).thenReturn(allCompetitors);

        CompetitorRepository competitorRepository = new CompetitorDefaultRepository(competitorContext);
        List<Competitor> allCompetitorsToTest = competitorRepository.getAll();
        if (allCompetitorsToTest == null){
            Assert.fail();
            return;
        }
        Assert.assertEquals(2, allCompetitorsToTest.size());
    }

    @Test
    public void getAllReturnsEmptyListWhenDataIsNull() {
        CompetitorContext competitorContext = mock(CompetitorContext.class);

        when(competitorContext.getAll()).thenReturn(null);

        CompetitorRepository competitorRepository = new CompetitorDefaultRepository(competitorContext);
        List<Competitor> allCompetitorsToTest = competitorRepository.getAll();
        Assert.assertNotNull(allCompetitorsToTest);
    }

    @Test
    public void updateUpdatesACompetitor() {
        CompetitorContext competitorContext = mock(CompetitorContext.class);

        Game game = new Game();
        game.setDescription("Test game");
        game.setName("Game");
        game.setId(1);

        Competitor competitorToUpdate = new Competitor();
        competitorToUpdate.setId(1);
        competitorToUpdate.setName("Test competitor");
        competitorToUpdate.setDescription("testCompetitor");
        competitorToUpdate.setGame(game);


        ArgumentCaptor<Competitor> argument = ArgumentCaptor.forClass(Competitor.class);
        doNothing().when(competitorContext).update(any(Competitor.class));

        CompetitorRepository competitorRepository = new CompetitorDefaultRepository(competitorContext);
        competitorRepository.update(competitorToUpdate);
        verify(competitorContext).update(argument.capture());
        Assert.assertEquals(argument.getValue().getName(), competitorToUpdate.getName());
        Assert.assertEquals(argument.getValue().getDescription(), competitorToUpdate.getDescription());
        Assert.assertEquals(argument.getValue().getId(), competitorToUpdate.getId());
        Assert.assertEquals(argument.getValue().getGame().getId(), competitorToUpdate.getGame().getId());
    }

    @Test
    public void deleteDeletesACompetitor() {
        CompetitorContext competitorContext = mock(CompetitorContext.class);

        int competitorId = 1;

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(int.class);
        doNothing().when(competitorContext).delete(anyInt());

        CompetitorRepository competitorRepository = new CompetitorDefaultRepository(competitorContext);
        competitorRepository.delete(competitorId);
        verify(competitorContext).delete(argument.capture());
        Assert.assertEquals(argument.getValue().intValue(), competitorId);
    }
}
