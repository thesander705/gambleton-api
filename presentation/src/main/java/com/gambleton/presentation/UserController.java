package com.gambleton.presentation;

import com.gambleton.factory.Factory;
import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.models.User;
import com.gambleton.presentation.viewModels.userController.ByAuthToken;
import com.gambleton.presentation.viewModels.userController.Credentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @PostMapping("/userByCredentials")
    public ResponseEntity<Object> getUserByCredentials(@RequestBody Credentials credentials) {
        UserLogic userLogic = Factory.getUserLogic();
        User user = userLogic.getByCredentials(credentials.getUsername(), credentials.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.resolve(200)).body(user);

    }

    @PostMapping("/userByAuthToken")
    public ResponseEntity<Object> getUserByCredentials(@RequestBody ByAuthToken authToken) {
        UserLogic userLogic = Factory.getUserLogic();
        User user = userLogic.getByAuthToken(authToken.getAuthToken());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.resolve(200)).body(user);

    }
}