package com.gambleton.logic;

import com.gambleton.logic.abstraction.CompetitorLogic;
import com.gambleton.models.Competitor;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.CompetitorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CompetitorDefaultLogicTest {
    @Test
    public void createCheckIfCompetitorGetsCreated() {
        CompetitorRepository competitorRepository = mock(CompetitorRepository.class);

        Game game = new Game();
        game.setDescription("Test game");
        game.setName("Game");
        game.setId(1);

        Competitor competitorToCreate = new Competitor();
        competitorToCreate.setName("Test competitor");
        competitorToCreate.setDescription("testCompetitor");
        competitorToCreate.setGame(game);

        ArgumentCaptor<Competitor> argument = ArgumentCaptor.forClass(Competitor.class);

        CompetitorLogic competitorLogic = new CompetitorDefaultLogic(competitorRepository);
        competitorLogic.createCompetitor(competitorToCreate.getName(), competitorToCreate.getDescription(), competitorToCreate.getGame().getId());

        verify(competitorRepository).create(argument.capture());

        Assert.assertNotNull(argument.getValue());
    }

    @Test
    public void getAllReturnsAllCompetitors() {
        CompetitorRepository competitorRepository = mock(CompetitorRepository.class);
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

        when(competitorRepository.getAll()).thenReturn(allCompetitors);

        CompetitorLogic competitorLogic = new CompetitorDefaultLogic(competitorRepository);
        List<Competitor> allCompetitorsToTest = competitorLogic.getAllCompetitors();
        if (allCompetitorsToTest == null){
            Assert.fail();
            return;
        }
        Assert.assertEquals(2, allCompetitorsToTest.size());
    }
}
