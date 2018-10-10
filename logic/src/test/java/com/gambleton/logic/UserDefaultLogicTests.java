package com.gambleton.logic;

import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.models.Role;
import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserRepository;
import org.junit.*;
import static org.mockito.Mockito.*;


public class UserDefaultLogicTests {

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

        UserLogic userLogic = new UserDefaultLogic(userRepository);
        User userFromLogic = userLogic.getByCredentials(username, password);

        Assert.assertEquals("", userFromLogic.getPassword());
    }

}
