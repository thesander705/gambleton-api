package com.gambleton.presentation;

import com.gambleton.factory.Factory;
import com.gambleton.logic.abstraction.GameLogic;
import com.gambleton.presentation.viewModels.gameController.CreateGame;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    GameLogic gameLogic;

    public GameController() {
        this.gameLogic = Factory.getGameLogic();
    }

    @PostMapping("/game")
    public ResponseEntity<Object> createGame(@RequestBody CreateGame game) {
        try {
            this.gameLogic.CreateGame(game.getName(), game.getDescription());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
