package com.gambleton.logic;

import com.gambleton.logic.abstraction.CompetitorLogic;
import com.gambleton.models.Competitor;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.CompetitorRepository;
import com.gambleton.repository.abstraction.GameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CompetitorDefaultLogicTest {
    @Test
    public void createCheckIfCompetitorGetsCreated() {
        CompetitorRepository competitorRepository = mock(CompetitorRepository.class);
        GameRepository gameRepository = mock(GameRepository.class);

        Game game = new Game();
        game.setDescription("Test game");
        game.setName("Game");
        game.setId(1);

        Competitor competitorToCreate = new Competitor();
        competitorToCreate.setName("Test competitor");
        competitorToCreate.setDescription("testCompetitor");
        competitorToCreate.setGame(game);

        ArgumentCaptor<Competitor> argument = ArgumentCaptor.forClass(Competitor.class);

        CompetitorLogic competitorLogic = new CompetitorDefaultLogic(competitorRepository, gameRepository);
        competitorLogic.createCompetitor(competitorToCreate.getName(), competitorToCreate.getDescription(), competitorToCreate.getGame().getId());

        verify(competitorRepository).create(argument.capture());

        Assert.assertNotNull(argument.getValue());
    }

    @Test
    public void getAllReturnsAllCompetitors() {
        CompetitorRepository competitorRepository = mock(CompetitorRepository.class);
        GameRepository gameRepository = mock(GameRepository.class);

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

        CompetitorLogic competitorLogic = new CompetitorDefaultLogic(competitorRepository, gameRepository);
        List<Competitor> allCompetitorsToTest = competitorLogic.getAllCompetitors();
        if (allCompetitorsToTest == null){
            Assert.fail();
            return;
        }
        Assert.assertEquals(2, allCompetitorsToTest.size());
    }

    @Test
    public void getCompetitorsByGameGetsAllCompetitorsByGame(){
        CompetitorRepository competitorRepository = mock(CompetitorRepository.class);
        GameRepository GameRepositoryStub = mock(GameRepository.class);

        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");

        Competitor competitor1 = new Competitor();
        competitor1.setName("Test competitor");
        competitor1.setDescription("This is a test competitor");
        competitor1.setGame(game);

        Competitor competitor2 = new Competitor();
        competitor2.setName("Test competitor2");
        competitor2.setDescription("This is a test competitor2");
        competitor2.setGame(game);

        List<Competitor> competitors = new ArrayList<Competitor>();
        competitors.add(competitor1);
        competitors.add(competitor2);

        when(competitorRepository.getCompetitorsByGame(anyInt())).thenReturn(competitors);

        CompetitorLogic competitorLogic = new CompetitorDefaultLogic(competitorRepository, GameRepositoryStub);
        List<Competitor> competitorsFromLogic = competitorLogic.getCompetitorsByGame(1);

        Assert.assertNotNull(competitorsFromLogic);
        Assert.assertEquals(2,competitorsFromLogic.size());
    }
}
