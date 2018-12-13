package com.gambleton.presentation;

import com.gambleton.factory.Factory;
import com.gambleton.logic.abstraction.CompetitorLogic;
import com.gambleton.logic.abstraction.GameLogic;
import com.gambleton.logic.abstraction.MatchLogic;
import com.gambleton.models.Game;
import com.gambleton.models.Match;
import com.gambleton.presentation.viewModels.matchController.CreateMatch;
import com.gambleton.presentation.viewModels.matchController.createMatch.BetOption;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableCaching
public class MatchController {
    MatchLogic matchLogic;
    CompetitorLogic competitorLogic;
    GameLogic gameLogic;

    public MatchController() {
        this.matchLogic = Factory.getMatchLogic();
        this.competitorLogic = Factory.getCompetitorLogic();
        this.gameLogic = Factory.getGameLogic();
    }

    @PostMapping("/match")
    @CacheEvict(value = "matches", allEntries = true)
    public ResponseEntity<Object> createMatch(@RequestBody CreateMatch match) {
        List<com.gambleton.models.BetOption> betOptions = new ArrayList<>();
        try {
            for (BetOption betoption : match.getBetOptions()) {
                betOptions.add(convertBetOptionViewModelToBetOption(betoption));
            }

            Game game = gameLogic.GetAllGames().stream().filter(x -> x.getId() == match.getGameId()).collect(Collectors.toList()).get(0);

            this.matchLogic.createMatch(match.getTitle(), match.getDescription(), betOptions, game, match.getStartDate(), match.getEndDate());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/match")
    @Cacheable("matches")
    public ResponseEntity<Object> getAllMatchs() {
        List<Match> matchs;
        try {
            matchs = this.matchLogic.getAllMatches();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(matchs);
    }

    private com.gambleton.models.BetOption convertBetOptionViewModelToBetOption(BetOption betOption) {
        com.gambleton.models.BetOption newBetOption = new com.gambleton.models.BetOption();
        newBetOption.setCompetitor(competitorLogic.getAllCompetitors().stream().filter(x -> x.getId() == betOption.getCompetitorId()).collect(Collectors.toList()).get(0));
        newBetOption.setPayoutRate(betOption.getPayoutRate());
        return newBetOption;
    }
}
