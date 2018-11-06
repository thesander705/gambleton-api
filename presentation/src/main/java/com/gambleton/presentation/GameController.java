package com.gambleton.presentation;

import com.gambleton.factory.Factory;
import com.gambleton.logic.abstraction.GameLogic;
import com.gambleton.presentation.viewModels.gameController.CreateGame;
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
    public void createGame(@RequestBody CreateGame game) {
        this.gameLogic.CreateGame(game.getName(), game.getDescription());
    }
}
