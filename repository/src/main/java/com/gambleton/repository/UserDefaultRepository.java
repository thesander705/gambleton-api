package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.UserContext;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserRepository;

import java.util.List;

public class UserDefaultRepository implements UserRepository {
    private UserContext userContext;

    public UserDefaultRepository(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    public User getByCredentials(String username, String password) {
        return null;
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }
}
