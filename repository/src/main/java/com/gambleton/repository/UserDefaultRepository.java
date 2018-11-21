package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.UserContext;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserDefaultRepository implements UserRepository {
    private UserContext userContext;

    public UserDefaultRepository(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    public User getByCredentials(String username, String password) {
        User userFromUserContext = userContext.getByUsername(username);

        if (userFromUserContext == null) {
            return null;
        }

        try {
            BCrypt.checkpw(password, userFromUserContext.getPassword());
        } catch (Exception e) {
            return null;
        }

        if (!BCrypt.checkpw(password, userFromUserContext.getPassword())) {
            return null;
        }

        return userFromUserContext;
    }

    @Override
    public User getByAuthToken(String authToken) {
        return this.userContext.getByAuthToken(authToken);
    }

    @Override
    public void create(User entity) {
        User userToCreate = entity;
        entity.setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt(8)));
        userContext.create(userToCreate);
    }

    @Override
    public User get(int id) {
        return userContext.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> users = userContext.getAll();

        if (users == null){
            users = new ArrayList<>();
        }
        
        return users;
    }

    @Override
    public void update(User entity) {
        userContext.update(entity);
    }

    @Override
    public void delete(int id) {
        userContext.delete(id);
    }
}
