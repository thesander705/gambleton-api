package com.gambleton.logic;

import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserRepository;

public class UserDefaultLogic implements UserLogic {

    private UserRepository userRepository;

    public UserDefaultLogic(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getByCredentials(String username, String password) {
        return userRepository.getByCredentials(username, password);
    }
}
