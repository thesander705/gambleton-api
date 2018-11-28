package com.gambleton.logic;

import com.gambleton.models.Role;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserRepository;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserDefaultLogicTest {

    @Test
    public void getByCredentials_returnsEmptyPassword(){
        String username = "test";
        String password = "Test123!";

        UserRepository userRepository = mock(UserRepository.class);

        User userFromMock = new User();
        userFromMock.setRole(Role.Gambler);
        userFromMock.setPassword(username);
        userFromMock.setUsername(password);
        userFromMock.setId(1);

        when(userRepository.getByCredentials(username,password)).thenReturn(userFromMock);

        UserDefaultLogic userLogic = new UserDefaultLogic(userRepository);
        User userFromLogic = userLogic.getByCredentials(username, password);

        Assert.assertEquals("", userFromLogic.getPassword());
    }

    @Test
    public void getByAuthToken_returnsEmptyPassword(){
        String authToken = "qwergbsa37242jmsakd";

        UserRepository userRepository = mock(UserRepository.class);

        User userFromMock = new User();
        userFromMock.setRole(Role.Gambler);
        userFromMock.setPassword("Test");
        userFromMock.setUsername("Test123!");
        userFromMock.setAuthToken(authToken);
        userFromMock.setId(1);

        when(userRepository.getByAuthToken(authToken)).thenReturn(userFromMock);

        UserDefaultLogic userLogic = new UserDefaultLogic(userRepository);
        User userFromLogic = userLogic.getByAuthToken(authToken);

        Assert.assertEquals("", userFromLogic.getPassword());
    }

}
