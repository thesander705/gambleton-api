package com.gambleton.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.gambleton.dataAccessLayer.abstraction.UserContext;
import com.gambleton.models.Role;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserRepository;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;

public class UserDefaultRepositoryTests {
    
    @Test
    public void getByCredentialsHashesPasswordBcryptEightRounds() {
        UserContext userContext = mock(UserContext.class);

        String password = "Password123!";

        User userFromContext = new User();
        userFromContext.setId(1);
        userFromContext.setUsername("test");
        userFromContext.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(8)));
        userFromContext.setRole(Role.Gambler);

        when(userContext.getByUsername("test")).thenReturn(userFromContext);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        User userFromRepository = userRepository.getByCredentials("test", password);
        assertNotNull(userFromRepository);
    }

    @Test
    public void createCheckIfPasswordGestHashed() {
        UserContext userContext = mock(UserContext.class);
        String password = "Password123!";

        User userToCreate = new User();
        userToCreate.setId(1);
        userToCreate.setRole(Role.Gambler);
        userToCreate.setUsername("Test");
        userToCreate.setPassword(password);

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        userRepository.create(userToCreate);

        verify(userContext).create(argument.capture());
        boolean passwordHashed = false;
        try{
            passwordHashed = BCrypt.checkpw(password, argument.getValue().getPassword());
        }catch(Exception ignored){

        }

        assertTrue(passwordHashed);
    }
}
