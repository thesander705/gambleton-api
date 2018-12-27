package com.gambleton.logic;

import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.models.*;
import com.gambleton.repository.abstraction.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class UserDefaultLogicTest {

    private User user;

    @Before
    public void setupModels() {
        Game game = new Game();
        game.setId(1);
        game.setName("Game");
        game.setDescription("This is a game");

        Competitor competitor1 = new Competitor();
        competitor1.setId(1);
        competitor1.setName("Competitor 1");
        competitor1.setDescription("This is competitor 1");
        competitor1.setGame(game);

        Competitor competitor2 = new Competitor();
        competitor2.setId(2);
        competitor2.setName("Competitor 1");
        competitor2.setDescription("This is competitor 1");
        competitor2.setGame(game);

        BetOption betOption1 = new BetOption();
        betOption1.setId(1);
        betOption1.setPayoutRate(2);
        betOption1.setCompetitor(competitor1);

        BetOption betOption2 = new BetOption();
        betOption2.setId(2);
        betOption2.setPayoutRate(1);
        betOption2.setCompetitor(competitor2);

        Bet bet1 = new Bet();
        bet1.setId(1);
        bet1.setMoneyPlaced(12);
        bet1.setBetOption(betOption1);

        Bet bet2 = new Bet();
        bet2.setId(2);
        bet2.setMoneyPlaced(11);
        bet2.setBetOption(betOption2);

        List<Bet> bets = new ArrayList<Bet>();
        bets.add(bet1);
        bets.add(bet2);

        this.user = new User();
        this.user.setId(1);
        this.user.setAuthToken("1234567890");
        this.user.setRole(Role.Gambler);
        this.user.setUsername("test");
        this.user.setPassword("Test123!");
        this.user.setMoney(123.22);
        this.user.setBets(bets);
    }

    @Test
    public void getByCredentials_returnsEmptyPassword() {
        String username = "test";
        String password = "Test123!";

        UserRepository userRepository = mock(UserRepository.class);

        User userFromMock = this.user;
        userFromMock.setPassword(username);
        userFromMock.setUsername(password);

        when(userRepository.getByCredentials(username, password)).thenReturn(userFromMock);

        UserDefaultLogic userLogic = new UserDefaultLogic(userRepository);
        User userFromLogic = userLogic.getByCredentials(username, password);

        Assert.assertEquals("", userFromLogic.getPassword());
    }

    @Test
    public void getByAuthToken_returnsEmptyPassword() {
        String authToken = "qwergbsa37242jmsakd";

        UserRepository userRepository = mock(UserRepository.class);

        User userFromMock = this.user;
        userFromMock.setAuthToken(authToken);

        when(userRepository.getByAuthToken(authToken)).thenReturn(userFromMock);

        UserDefaultLogic userLogic = new UserDefaultLogic(userRepository);
        User userFromLogic = userLogic.getByAuthToken(authToken);

        Assert.assertEquals("", userFromLogic.getPassword());
    }

    @Test
    public void getByCredentialsReturnsNullWhenUserIsNotFound() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getByCredentials(anyString(), anyString())).thenReturn(null);

        UserDefaultLogic userDefaultLogic = new UserDefaultLogic(userRepository);
        User userFromLogic = userDefaultLogic.getByCredentials("test", "Test123!");
        assertNull(userFromLogic);
    }

    @Test
    public void getByAuthTokenReturnsNullWhenUserIsNotFound() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getByAuthToken(anyString())).thenReturn(null);

        UserDefaultLogic userDefaultLogic = new UserDefaultLogic(userRepository);
        User userFromLogic = userDefaultLogic.getByAuthToken("qwergbsa37242jmsakd");
        assertNull(userFromLogic);
    }

    @Test
    public void getByCredentialsReturnsUserWhenCorrectAuthToken() {
        UserRepository userRepository = mock(UserRepository.class);
        String username = "test";
        String password = "Passoword123!";

        User userFromRepository = this.user;
        userFromRepository.setUsername(username);
        userFromRepository.setPassword(password);

        when(userRepository.getByCredentials(username, password)).thenReturn(userFromRepository);

        UserLogic userLogic = new UserDefaultLogic(userRepository);
        User userFromLogic = userLogic.getByCredentials(username, password);
        assertNotNull(userFromLogic);
    }

    @Test
    public void getByAuthTokenReturnsUserWhenCorrectAuthToken() {
        UserRepository userRepository = mock(UserRepository.class);
        String authToken = "12345sdfghxcvbn";

        User userFromRepository = this.user;
        userFromRepository.setAuthToken(authToken);

        when(userRepository.getByAuthToken(authToken)).thenReturn(userFromRepository);

        UserLogic userLogic = new UserDefaultLogic(userRepository);
        User userFromLogic = userLogic.getByAuthToken(authToken);
        assertNotNull(userFromLogic);
    }

    @Test
    public void placeBetsPassesCorrectData(){
        UserRepository userRepository = mock(UserRepository.class);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);

        UserLogic matchLogic = new UserDefaultLogic(userRepository);

        matchLogic.placeBet(this.user.getId(), this.user.getBets().get(0).getBetOption().getId(), 12);

        verify(userRepository).update(argument.capture());

        Assert.assertEquals(3,argument.getValue().getBets().size());
        Assert.assertEquals(this.user.getBets().get(0).getBetOption().getId(),argument.getValue().getBets().get(2).getBetOption().getId());
        Assert.assertEquals(12,argument.getValue().getBets().get(2).getMoneyPlaced(), 0);
        Assert.assertEquals(this.user.getId(),argument.getValue().getId());
    }
}
