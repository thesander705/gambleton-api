package com.gambleton.logic;

import com.gambleton.logic.abstraction.MatchLogic;
import com.gambleton.models.BetOption;
import com.gambleton.models.Competitor;
import com.gambleton.models.Match;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.MatchRepository;
import com.gambleton.repository.abstraction.GameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MatchDefaultLogicTest {
    @Test
    public void createCheckIfMatchGetsCreated() {
        MatchRepository matchRepository = mock(MatchRepository.class);

        Game game = new Game();
        game.setDescription("Tennis game");
        game.setName("Tennis");

        Competitor competitor1 = new Competitor();
        competitor1.setDescription("Test competitor");
        competitor1.setName("Test competitor");
        competitor1.setGame(game);

        Competitor competitor2 = new Competitor();
        competitor2.setDescription("Test competitor2");
        competitor2.setName("Test competitor2");
        competitor2.setGame(game);

        BetOption betOption1 = new BetOption();
        betOption1.setPayoutRate(2);
        betOption1.setCompetitor(competitor1);

        BetOption betOption2 = new BetOption();
        betOption2.setPayoutRate(1);
        betOption2.setCompetitor(competitor2);

        Match match = new Match();
        match.setTitle("name");
        match.setDescription("description");
        List<BetOption> betOptions = new ArrayList<>();
        betOptions.add(betOption1);
        betOptions.add(betOption2);
        match.setBetOptions(betOptions);
        Calendar calendar = new Calendar.Builder().build();
        calendar.set(2018, 11, 13, 20, 30);
        Date date = calendar.getTime();
        match.setStartDate(date);
        calendar.set(2018, 11, 13, 22, 30);
        date = calendar.getTime();
        match.setEndDate(date);
        ArgumentCaptor<Match> argument = ArgumentCaptor.forClass(Match.class);

        MatchLogic matchLogic = new MatchDefaultLogic(matchRepository);
        matchLogic.createMatch(match.getTitle(), match.getDescription(), betOptions, game, match.getStartDate(), match.getEndDate());

        verify(matchRepository).create(argument.capture());

        Assert.assertNotNull(argument.getValue());
    }

    @Test
    public void getAllReturnsAllMatchs() {
        MatchRepository matchRepository = mock(MatchRepository.class);
        GameRepository gameRepository = mock(GameRepository.class);

        Game game = new Game();
        game.setDescription("Tennis game");
        game.setName("Tennis");

        Competitor competitor1 = new Competitor();
        competitor1.setDescription("Test competitor");
        competitor1.setName("Test competitor");
        competitor1.setGame(game);

        Competitor competitor2 = new Competitor();
        competitor2.setDescription("Test competitor2");
        competitor2.setName("Test competitor2");
        competitor2.setGame(game);

        BetOption betOption1 = new BetOption();
        betOption1.setPayoutRate(2);
        betOption1.setCompetitor(competitor1);

        BetOption betOption2 = new BetOption();
        betOption2.setPayoutRate(1);
        betOption2.setCompetitor(competitor2);

        Match matchFromContext1 = new Match();
        matchFromContext1.setTitle("name");
        matchFromContext1.setDescription("description");
        List<BetOption> betOptions = new ArrayList<>();
        betOptions.add(betOption1);
        betOptions.add(betOption2);
        matchFromContext1.setBetOptions(betOptions);
        Calendar calendar = new Calendar.Builder().build();
        calendar.set(2018, 11, 13, 20, 30);
        Date date = calendar.getTime();
        matchFromContext1.setStartDate(date);
        calendar.set(2018, 11, 13, 22, 30);
        date = calendar.getTime();
        matchFromContext1.setEndDate(date);

        Match matchFromContext2 = new Match();
        matchFromContext2.setId(2);
        matchFromContext2.setTitle("Test match2");
        matchFromContext2.setDescription("testMatch");
        matchFromContext2.setBetOptions(betOptions);
        calendar.set(2018, 11, 13, 20, 30);
        date = calendar.getTime();
        matchFromContext2.setStartDate(date);
        calendar.set(2018, 11, 13, 22, 30);
        date = calendar.getTime();
        matchFromContext2.setEndDate(date);

        ArrayList<Match> allMatchs = new ArrayList<Match>();
        allMatchs.add(matchFromContext1);
        allMatchs.add(matchFromContext2);

        when(matchRepository.getAll()).thenReturn(allMatchs);

        MatchLogic matchLogic = new MatchDefaultLogic(matchRepository);
        List<Match> allMatchsToTest = matchLogic.getAllMatches();
        if (allMatchsToTest == null){
            Assert.fail();
            return;
        }
        Assert.assertEquals(2, allMatchsToTest.size());
    }
}
