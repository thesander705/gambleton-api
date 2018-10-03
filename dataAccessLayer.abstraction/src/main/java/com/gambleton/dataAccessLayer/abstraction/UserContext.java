package com.gambleton.dataAccessLayer.abstraction;

import com.gambleton.models.User;

public interface UserContext extends Context<User> {
    /**
     * @param username the username of the user you'd like to get
     * @param password the password of the user you'd like to get
     * @return the user you wanted.
     */
    User getByCredentials(String username, String password);
}
