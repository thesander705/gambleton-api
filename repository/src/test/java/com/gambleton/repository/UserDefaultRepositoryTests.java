package com.gambleton.repository;

import com.gambleton.dataAccessLayer.abstraction.UserContext;
import com.gambleton.models.Role;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserDefaultRepositoryTests {
    @Test
    public void getByCredentialsReturnsNullWhenUserIsNotFound(){
        UserContext userContext = mock(UserContext.class);
        when(userContext.getByUsername(anyString())).thenReturn(null);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        User userFromRepository = userRepository.getByCredentials("test", "Test123!");
        assertNull(userFromRepository);

    }

    @Test
    public void getByCredentialsReturnsNullWhenHashFails(){
        UserContext userContext = mock(UserContext.class);

        String password = "Password123!";

        User userFromContext = new User();
        userFromContext.setId(1);
        userFromContext.setUsername("test");
        userFromContext.setPassword(password);
        userFromContext.setRole(Role.Gambler);

        when(userContext.getByUsername("test")).thenReturn(userFromContext);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        User userFromRepository = userRepository.getByCredentials("test", password);
        assertNull(userFromRepository);

    }

    @Test
    public void getByCredentialsReturnsNullWhenPasswordIsWrong(){
        UserContext userContext = mock(UserContext.class);

        String password = "Password123!";

        User userFromContext = new User();
        userFromContext.setId(1);
        userFromContext.setUsername("test");
        userFromContext.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(8)));
        userFromContext.setRole(Role.Gambler);

        when(userContext.getByUsername("test")).thenReturn(userFromContext);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        User userFromRepository = userRepository.getByCredentials("test", "Kaas123!");
        assertNull(userFromRepository);

    }

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
    public void createCheckIfUserGetsCreated() {
        UserContext userContext = mock(UserContext.class);

        User userToCreate = new User();
        userToCreate.setRole(Role.Gambler);
        userToCreate.setUsername("Test");
        userToCreate.setPassword("Password123!");
        userToCreate.setAuthToken("1234567asdsvsd");

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        userRepository.create(userToCreate);

        verify(userContext).create(argument.capture());

        Assert.assertNotNull(argument.getValue());
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

    @Test
    public void getByAuthTokenReturnsUserWhenCorrectAuthToken(){
        UserContext userContext = mock(UserContext.class);
        String authToken = "12345sdfghxcvbn";

        User userFromContext = new User();
        userFromContext.setId(1);
        userFromContext.setUsername("test");
        userFromContext.setPassword(BCrypt.hashpw("Test123!", BCrypt.gensalt(8)));
        userFromContext.setRole(Role.Gambler);
        userFromContext.setAuthToken(authToken);

        when(userContext.getByAuthToken(authToken)).thenReturn(userFromContext);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        User userFromRepository = userRepository.getByAuthToken(authToken);
        assertNotNull(userFromRepository);
    }

    @Test
    public void getByAuthTokenReturnsNullWhenIncorrectAuthToken(){
        UserContext userContext = mock(UserContext.class);
        String authToken = "12345sdfghxcvbn";

        when(userContext.getByAuthToken(anyString())).thenReturn(null);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        User userFromRepository = userRepository.getByAuthToken(authToken);
        assertNull(userFromRepository);
    }

    @Test
    public void getReturnsUserWhenCorrectId() {
        UserContext userContext = mock(UserContext.class);
        int id = 1;

        User userFromContext = new User();
        userFromContext.setId(id);
        userFromContext.setUsername("test");
        userFromContext.setPassword(BCrypt.hashpw("Test123!", BCrypt.gensalt(8)));
        userFromContext.setRole(Role.Gambler);
        userFromContext.setAuthToken("12345sdfghxcvbn");

        when(userContext.get(id)).thenReturn(userFromContext);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        User userFromRepository = userRepository.get(id);
        assertNotNull(userFromRepository);
    }

    @Test
    public void getReturnsNullWhenIncorrectId() {
        UserContext userContext = mock(UserContext.class);
        int id = 1;

        User userFromContext = new User();
        userFromContext.setId(id);
        userFromContext.setUsername("test");
        userFromContext.setPassword(BCrypt.hashpw("Test123!", BCrypt.gensalt(8)));
        userFromContext.setRole(Role.Gambler);
        userFromContext.setAuthToken("12345sdfghxcvbn");

        when(userContext.get(id)).thenReturn(userFromContext);

        UserRepository userRepository = new UserDefaultRepository(userContext);
        User userFromRepository = userRepository.get(2);
        assertNull(userFromRepository);
    }
}
