package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.MatchContext;
import com.gambleton.models.BetOption;
import com.gambleton.models.Competitor;
import com.gambleton.models.Match;
import com.gambleton.models.Game;
import com.gambleton.repository.abstraction.MatchRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class MatchRepositoryTest {
    @Test
    public void createCheckIfMatchGetsCreated() {
        MatchContext matchContext = mock(MatchContext.class);

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

        MatchRepository matchRepository = new MatchDefaultRepository(matchContext);
        matchRepository.create(match);

        verify(matchContext).create(argument.capture());

        Assert.assertNotNull(argument.getValue());
    }

    @Test
    public void getReturnsMatchWhenCorrectId() {
        MatchContext matchContext = mock(MatchContext.class);

        int id = 1;

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
        match.setId(id);
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

        when(matchContext.get(id)).thenReturn(match);

        MatchRepository matchRepository = new MatchDefaultRepository(matchContext);
        Match matchFromRepository = matchRepository.get(id);
        assertNotNull(matchFromRepository);
    }


    @Test
    public void getAllReturnsAllMatchs() {
        MatchContext matchContext = mock(MatchContext.class);

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

        Match match1 = new Match();
        match1.setTitle("name");
        match1.setDescription("description");
        List<BetOption> betOptions = new ArrayList<>();
        betOptions.add(betOption1);
        betOptions.add(betOption2);
        match1.setBetOptions(betOptions);

        Calendar calendar = new Calendar.Builder().build();
        calendar.set(2018, 11, 13, 20, 30);
        Date date = calendar.getTime();
        match1.setStartDate(date);
        calendar.set(2018, 11, 13, 22, 30);
        date = calendar.getTime();
        match1.setEndDate(date);

        Match match2 = new Match();
        match2.setTitle("name");
        match2.setDescription("description");
        betOptions.add(betOption1);
        betOptions.add(betOption2);
        match2.setBetOptions(betOptions);
        calendar.set(2018, 11, 13, 20, 30);
        date = calendar.getTime();
        match2.setStartDate(date);
        calendar.set(2018, 11, 13, 22, 30);
        date = calendar.getTime();
        match2.setEndDate(date);

        ArrayList<Match> allMatchs = new ArrayList<Match>();
        allMatchs.add(match1);
        allMatchs.add(match2);

        when(matchContext.getAll()).thenReturn(allMatchs);

        MatchRepository matchRepository = new MatchDefaultRepository(matchContext);
        List<Match> allMatchsToTest = matchRepository.getAll();
        if (allMatchsToTest == null){
            Assert.fail();
            return;
        }
        Assert.assertEquals(2, allMatchsToTest.size());
    }

    @Test
    public void getAllReturnsEmptyListWhenDataIsNull() {
        MatchContext matchContext = mock(MatchContext.class);

        when(matchContext.getAll()).thenReturn(null);

        MatchRepository matchRepository = new MatchDefaultRepository(matchContext);
        List<Match> allMatchsToTest = matchRepository.getAll();
        Assert.assertNotNull(allMatchsToTest);
    }

    @Test
    public void updateUpdatesAMatch() {
        MatchContext matchContext = mock(MatchContext.class);

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
        doNothing().when(matchContext).update(any(Match.class));

        MatchRepository matchRepository = new MatchDefaultRepository(matchContext);
        matchRepository.update(match);
        verify(matchContext).update(argument.capture());
        Assert.assertEquals(argument.getValue().getTitle(), match.getTitle());
        Assert.assertEquals(argument.getValue().getDescription(), match.getDescription());
        Assert.assertEquals(argument.getValue().getId(), match.getId());
        Assert.assertEquals(argument.getValue().getBetOptions().get(0).getId(), match.getBetOptions().get(0).getId());
        Assert.assertEquals(argument.getValue().getBetOptions().get(1).getId(), match.getBetOptions().get(1).getId());
        Assert.assertEquals(argument.getValue().getStartDate(), match.getStartDate());
        Assert.assertEquals(argument.getValue().getEndDate(), match.getEndDate());
    }

    @Test
    public void deleteDeletesAMatch() {
        MatchContext matchContext = mock(MatchContext.class);

        int matchId = 1;

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(int.class);
        doNothing().when(matchContext).delete(anyInt());

        MatchRepository matchRepository = new MatchDefaultRepository(matchContext);
        matchRepository.delete(matchId);
        verify(matchContext).delete(argument.capture());
        Assert.assertEquals(argument.getValue().intValue(), matchId);
    }

    @Test
    public void getMatchesByGameReturnsAllGamesByThatMatch(){
        MatchContext matchContext = mock(MatchContext.class);

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

        Match match1 = new Match();
        match1.setTitle("name");
        match1.setDescription("description");
        List<BetOption> betOptions = new ArrayList<>();
        betOptions.add(betOption1);
        betOptions.add(betOption2);
        match1.setBetOptions(betOptions);

        Calendar calendar = new Calendar.Builder().build();
        calendar.set(2018, 11, 13, 20, 30);
        Date date = calendar.getTime();
        match1.setStartDate(date);
        calendar.set(2018, 11, 13, 22, 30);
        date = calendar.getTime();
        match1.setEndDate(date);

        Match match2 = new Match();
        match2.setTitle("name");
        match2.setDescription("description");
        betOptions.add(betOption1);
        betOptions.add(betOption2);
        match2.setBetOptions(betOptions);
        calendar.set(2018, 11, 13, 20, 30);
        date = calendar.getTime();
        match2.setStartDate(date);
        calendar.set(2018, 11, 13, 22, 30);
        date = calendar.getTime();
        match2.setEndDate(date);

        ArrayList<Match> allMatchs = new ArrayList<Match>();
        allMatchs.add(match1);
        allMatchs.add(match2);

        when(matchContext.getMatchesByGame(anyInt())).thenReturn(allMatchs);

        MatchRepository matchRepository = new MatchDefaultRepository(matchContext);
        List<Match> allMatchsToTest = matchRepository.getMatchesByGame(1);
        if (allMatchsToTest == null){
            Assert.fail();
            return;
        }
        Assert.assertEquals(2, allMatchsToTest.size());
    }
}
