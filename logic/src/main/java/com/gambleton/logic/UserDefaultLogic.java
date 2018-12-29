package com.gambleton.logic;

import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.models.Bet;
import com.gambleton.models.BetOption;
import com.gambleton.models.Match;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.MatchRepository;
import com.gambleton.repository.abstraction.UserRepository;

import java.security.InvalidParameterException;
import java.util.List;

public class UserDefaultLogic implements UserLogic {

    private UserRepository userRepository;
    private MatchRepository matchRepository;

    public UserDefaultLogic(UserRepository userRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public User getByCredentials(String username, String password) {
        User user = userRepository.getByCredentials(username, password);
        if(user == null){
            return null;
        }
        user.setPassword("");
        return user;
    }

    @Override
    public User getByAuthToken(String authToken) {
        User user = userRepository.getByAuthToken(authToken);
        if(user == null){
            return null;
        }
        user.setPassword("");
        return user;
    }

    @Override
    public void placeBet(int userPlacingBetId, int betOptionId, double amountOfMoney) throws Exception{
        User user = this.userRepository.get(userPlacingBetId);
        if (user.getMoney() < amountOfMoney){
            throw new Exception("Not enough money");
        }

        user.setMoney(user.getMoney() - amountOfMoney);

        List<Match> matches = matchRepository.getAll();
        BetOption betOption = null;

        for (Match match : matches) {
            for (BetOption betOptionByMatch : match.getBetOptions()) {
                if (betOptionByMatch.getId() == betOptionId){
                    betOption = betOptionByMatch;
                }
            }
        }

        if (betOption == null){
            throw new InvalidParameterException();
        }

        Bet bet = new Bet();
        bet.setMoneyPlaced(amountOfMoney);
        bet.setBetOption(betOption);
        user.getBets().add(bet);

        this.userRepository.update(user);
    }
}
