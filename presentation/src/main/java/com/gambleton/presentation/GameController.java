package com.gambleton.presentation;

import com.gambleton.factory.Factory;
import com.gambleton.logic.abstraction.CompetitorLogic;
import com.gambleton.logic.abstraction.GameLogic;
import com.gambleton.logic.abstraction.MatchLogic;
import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.models.*;
import com.gambleton.presentation.viewModels.gameController.CreateGame;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("Duplicates")
@RestController
@EnableCaching
public class GameController {
    private final CompetitorLogic competitorLogic;
    private final UserLogic userLogic;
    private GameLogic gameLogic;
    private MatchLogic matchLogic;

    public GameController() {
        this.gameLogic = Factory.getGameLogic();
        this.matchLogic = Factory.getMatchLogic();
        this.competitorLogic = Factory.getCompetitorLogic();
        this.userLogic = Factory.getUserLogic();
    }

    @PostMapping("/game")
    @CacheEvict(value = "games", allEntries = true)
    public ResponseEntity<Object> createGame(@RequestBody CreateGame game) {
        try {
            User requestingUser = this.userLogic.getByAuthToken(game.getAuthToken());

            if (requestingUser == null || requestingUser.getRole() != Role.Administrator){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            this.gameLogic.CreateGame(game.getName(), game.getDescription());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/game")
    @Cacheable("games")
    public ResponseEntity<Object> getAllGames() {
        List<Game> games;
        try {
            games = this.gameLogic.GetAllGames();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(games);
    }

    @GetMapping("/game/{gameId}")
    @Cacheable(value = "game", key = "#gameId")
    public ResponseEntity<Object> getGame(@PathVariable int gameId) {
        Game game;
        try {
            game = this.gameLogic.getGame(gameId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(game);
    }

    @GetMapping("/game/{gameId}/match")
    @Cacheable(value = "matchesByGame", key = "#gameId")
    public ResponseEntity<Object> getMatchesByGame(@PathVariable int gameId) {
        List<Match> matches;
        try {
            matches = this.matchLogic.getMatchesByGame(gameId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(matches);
    }

    @GetMapping("/game/{gameId}/competitor")
    @Cacheable(value = "competitorsByGame", key = "#gameId")
    public ResponseEntity<Object> getCompetitorsByGame(@PathVariable int gameId) {
        List<Competitor> competitors;
        try {
            competitors = this.competitorLogic.getCompetitorsByGame(gameId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(competitors);
    }
}
