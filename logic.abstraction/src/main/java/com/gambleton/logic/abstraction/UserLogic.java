package com.gambleton.logic.abstraction;

import com.gambleton.models.User;

public interface UserLogic {
    User getByCredentials(String username, String password);

    User getByAuthToken(String authToken);
}
