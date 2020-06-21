package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.GetUserReduce;
import edu.utn.TpCellphone.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    
    @Mock
    private ResponseEntity<GetUserReduce> userReduce;
    
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
        User user = new User(1, "123", "juan", "Perez", "juancioto", "123abc", null, new City(1, "mar del plata", 223, null));
        when(service.addUser(user)).thenReturn(userReduce);
        ResponseEntity<GetUserReduce> response = userController.addUser(user);
        
        assertNotNull(response);
        assertEquals(userReduce, response);
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
    public void updateTest() throws NoSuchAlgorithmException {
        User userToUpdate = new User(1, "123", "juan", "Perez", "juancioto", "123abc", null, null);
        when(service.update(userToUpdate, 1)).thenReturn(userToUpdate);
        User response = userController.update(userToUpdate, 1);
        
        assertNotNull(response);
        assertEquals(userToUpdate, response);
    }
    
    @Test
    public void loginTest() throws NoSuchAlgorithmException {
        User user = new User();
        String username = "jdromerorajoy";
        String password = "12345678";
        String type = "client";
        user.setUsername(username);
        user.setPassword(password);
        when(service.login(username, password, type)).thenReturn(user);
        User response = userController.login(username, password, type);
        
        assertNotNull(response);
        assertEquals(user, response);
        verify(service, times(1)).login(username, password, type);
    }
    
    @Test()
    public void ExceptionLoginTest() throws NoSuchAlgorithmException {
        User user = new User();
        String username = "nicopatoco";
        String password = "abc123";
        String type = "admin";
        user.setUsername(username);
        user.setPassword(password);
        doThrow(new RuntimeException("User does not exists")).when(service).login(username, password, type);
        
        assertThrows(RuntimeException.class, () -> {
            userController.login(username, password, type);
        });
    }
}
