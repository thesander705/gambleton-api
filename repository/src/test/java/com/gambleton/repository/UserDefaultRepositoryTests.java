package com.gambleton.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.gambleton.dataAccessLayer.abstraction.UserContext;
import com.gambleton.models.Role;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserRepository;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

public class UserDefaultRepositoryTests {

    @Test
    public void getByCredentials_HashesPasswordBcryptEightRounds() {
        UserContext userContext = mock(UserContext.class);

        String Password = "Password123!";

        User userFromContext = new User();
        userFromContext.setId(1);
        userFromContext.setUsername("test");
        userFromContext.setPassword(BCrypt.hashpw(Password, BCrypt.gensalt(8)));
        userFromContext.setRole(Role.Gambler);

        when(userContext.getByUsername("test")).thenReturn(userFromContext);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        User userFromRepository = userRepository.getByCredentials("test", Password);
        assertNotNull(userFromRepository);
    }
}
