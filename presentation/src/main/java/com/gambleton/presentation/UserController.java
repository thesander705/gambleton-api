package com.gambleton.presentation;

import com.gambleton.factory.Factory;
import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @PostMapping("/userByCredentials")
    public User getUserByCredentials(String username, String password) {
        UserLogic userLogic = Factory.getUserLogic();
        User user = userLogic.getByCredentials(username, password);
        return user;
    }
}
