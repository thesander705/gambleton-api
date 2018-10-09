package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.UserContext;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

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
            if (BCrypt.checkpw(password, userFromUserContext.getPassword())) {
                return userFromUserContext;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
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
        return userContext.getAll();
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
