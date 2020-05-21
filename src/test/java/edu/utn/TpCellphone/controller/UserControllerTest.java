package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.model.Users;
import edu.utn.TpCellphone.service.CityService;
import edu.utn.TpCellphone.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Users user = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        when(service.getById(1)).thenReturn(Optional.ofNullable(user));
        Optional<Users> response = userController.getUserById(1);
        
        assertNotNull(response);
        assertEquals(user, response.get());
    }
    
    @Test
    public void nullGetUserByIdTest() {
        when(service.getById(1)).thenReturn(Optional.ofNullable(null));
        Optional<Users> response = userController.getUserById(1);
        
        assertTrue(response.isEmpty());
    }
    
    @Test
    public void addUserTest() {
        Users user = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        when(service.addUser(user)).thenReturn(user);
        Users response = userController.addUser(user);
        
        assertNotNull(response);
        assertEquals(user, response);
    }
    
    @Test
    public void getAllTest() {
        List<Users> usersList = new ArrayList<>();
        Users user1 = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        Users user2 = new Users(1,"333","nico","herrera", "nicopatoco", "123abc", 1, null, null, null );
        usersList.add(user1);
        usersList.add(user2);
        
        when(service.getAll("nico")).thenReturn(usersList);
        List<Users> response = userController.getAll("nico");
        
        assertNotNull(response);
        assertEquals(usersList, response);
    }
    
    @Test
    public void getAllTest2() {
        List<Users> usersList = new ArrayList<>();
        Users user1 = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        Users user2 = new Users(1,"333","nico","herrera", "nicopatoco", "123abc", 1, null, null, null );
        usersList.add(user1);
        usersList.add(user2);
        
        when(service.getAll(null)).thenReturn(usersList);
        List<Users> response = userController.getAll(null);
        
        assertNotNull(response);
        assertEquals(usersList, response);
    }
    
    @Test
    public void deleteCityTest() {
        Users user = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        doNothing().when(service).delete(user);
        userController.deleteUser(user);
        
        verify(service, times(1)).delete(user);
    }
    
    @Test
    public void updateTest() {
        Users userToUpdate = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        when(service.update(userToUpdate, 1)).thenReturn(userToUpdate);
        Users response = userController.update(userToUpdate, 1);
        
        assertNotNull(response);
        assertEquals(userToUpdate, response);
    }
}
