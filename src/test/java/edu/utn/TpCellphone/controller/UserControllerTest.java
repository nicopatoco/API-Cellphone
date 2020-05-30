package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    
    @Mock
    private UserService service;
    
    @Test
    public void getUserByIdTest() {
        User user = new User(1, "123", "juan", "Perez", "juancioto", "123abc", null, null);
        when(service.getById(1)).thenReturn(Optional.ofNullable(user));
        Optional<User> response = userController.getUserById(1);
        
        assertNotNull(response);
        assertEquals(user, response.get());
    }
    
    @Test
    public void nullGetUserByIdTest() {
        when(service.getById(1)).thenReturn(Optional.ofNullable(null));
        Optional<User> response = userController.getUserById(1);
        
        assertTrue(response.isEmpty());
    }
    
    @Test
    public void addUserTest() {
        User user = new User(1, "123", "juan", "Perez", "juancioto", "123abc", null, null);
        when(service.addUser(user)).thenReturn(user);
        User response = userController.addUser(user);
        
        assertNotNull(response);
        assertEquals(user, response);
    }
    
    @Test
    public void getAllTest() {
        List<User> usersList = new ArrayList<>();
        User user1 = new User(1, "123", "juan", "Perez", "juancioto", "123abc", null, null);
        User user2 = new User(1, "333", "nico", "herrera", "nicopatoco", "123abc", null, null);
        usersList.add(user1);
        usersList.add(user2);
        
        when(service.getAll("nico")).thenReturn(usersList);
        List<User> response = userController.getAll("nico");
        
        assertNotNull(response);
        assertEquals(usersList, response);
    }
    
    @Test
    public void getAllTest2() {
        List<User> usersList = new ArrayList<>();
        User user1 = new User(1, "123", "juan", "Perez", "juancioto", "123abc", null, null);
        User user2 = new User(1, "333", "nico", "herrera", "nicopatoco", "123abc", null, null);
        usersList.add(user1);
        usersList.add(user2);
        
        when(service.getAll(null)).thenReturn(usersList);
        List<User> response = userController.getAll(null);
        
        assertNotNull(response);
        assertEquals(usersList, response);
    }
    
    @Test
    public void deleteCityTest() {
        User user = new User(1, "123", "juan", "Perez", "juancioto", "123abc", null, null);
        doNothing().when(service).delete(user);
        userController.deleteUser(user);
        
        verify(service, times(1)).delete(user);
    }
    
    @Test
    public void updateTest() {
        User userToUpdate = new User(1, "123", "juan", "Perez", "juancioto", "123abc", null, null);
        when(service.update(userToUpdate, 1)).thenReturn(userToUpdate);
        User response = userController.update(userToUpdate, 1);
        
        assertNotNull(response);
        assertEquals(userToUpdate, response);
    }
    
    @Test
    public void loginTest() {
        User user = new User();
        String username = "nicopatoco";
        String password = "abc123";
        user.setUsername(username);
        user.setPassword(password);
        when(service.login(username, password)).thenReturn(user);
        User response = userController.login(username, password);
        
        assertNotNull(response);
        assertEquals(user, response);
        verify(service, times(1)).login(username, password);
    }
    
    @Test()
    public void ExceptionLoginTest() {
        User user = new User();
        String username = "nicopatoco";
        String password = "abc123";
        user.setUsername(username);
        user.setPassword(password);
        doThrow(new RuntimeException("User does not exists")).when(service).login(username, password);
        
        assertThrows(RuntimeException.class, () -> {
            userController.login(username, password);
        });
    }
}
