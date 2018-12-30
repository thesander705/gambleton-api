package com.gambleton.presentation;

import com.gambleton.factory.Factory;
import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.models.User;
import com.gambleton.presentation.viewModels.userController.ByAuthToken;
import com.gambleton.presentation.viewModels.userController.Credentials;
import com.gambleton.presentation.viewModels.userController.PlaceBets;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableCaching
public class UserController {

    @PostMapping("/userByCredentials")
    @Cacheable(value = "user", key = "#credentials.username")
    public ResponseEntity<Object> getUserByCredentials(@RequestBody Credentials credentials) {
        UserLogic userLogic = Factory.getUserLogic();
        User user = userLogic.getByCredentials(credentials.getUsername(), credentials.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.resolve(200)).body(user);
    }

    @PostMapping("/userByAuthToken")
    @Cacheable(value = "user", key = "#authToken.authToken")
    public ResponseEntity<Object> getUserByAuthToken(@RequestBody ByAuthToken authToken) {
        UserLogic userLogic = Factory.getUserLogic();
        User user = userLogic.getByAuthToken(authToken.getAuthToken());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.resolve(200)).body(user);
    }

    @PostMapping("/bets")
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<Object> placeBets(@RequestBody PlaceBets placeBetsParams) {
        UserLogic userLogic = Factory.getUserLogic();

        try {
            userLogic.placeBet(placeBetsParams.getUserPlacingBetId(), placeBetsParams.getBetOptionId(), placeBetsParams.getAmountOfMoney());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.resolve(500)).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.resolve(200)).body(null);
    }
}
