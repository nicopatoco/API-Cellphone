package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Users;
import edu.utn.TpCellphone.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    
    @Mock
    private UserRepository repository;

    @Test
    public void addClientTest() {
        Users user = new Users();
        user.setId_user(1);
        user.setId("35885684");
        user.setName("nicolas");
        user.setName("surname");
        when(repository.save(user)).thenReturn(user);
        Users response = userService.addUser(user);

        assertNotNull(response);
        assertEquals(user, response);
    }

    @Test
    public void getByIdTest() {
        Users user = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        when(repository.findById(5)).thenReturn(java.util.Optional.ofNullable(user));
        Optional<Users> response = userService.getById(5);
    
        assertNotNull(response);
        assertEquals(user, response.get());
    }

    @Test
    public void getAllTest() {
        Users user1 = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        Users user2 = new Users(1,"333","nico","herrera", "nicopatoco", "123abc", 1, null, null, null );
        List<Users> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);
        when(repository.findAll()).thenReturn(usersList);
        List<Users> response = userService.getAll(null);
    
        assertNotNull(response);
        assertEquals(usersList, response);
    }

    @Test
    public void getAllTest2() {
        Users user = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        List<Users> usersList = new ArrayList<>();
        usersList.add(user);
        when(repository.findByName("juan")).thenReturn(usersList);
        List<Users> response = userService.getAll("juan");
    
        assertNotNull(response);
        assertEquals(usersList, response);
    }
    
    @Test
    public void deleteTest() {
        Users user = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        doNothing().when(repository).delete(user);
        userService.delete(user);

        verify(repository, times(1)).delete(user);
    }
    
    @Test
    public void updateTest() {
        Users user = new Users(1,"333","nico","herrera", "nicopatoco", "123abc", 1, null, null, null );
        Users userToUpdate = new Users(1,"123","juan","Perez", "juancioto", "123abc", 1, null, null, null );
        int idToUpdate = 1;
        
        when(repository.getOne(idToUpdate)).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        Users response = userService.update(userToUpdate, idToUpdate);
    
        Assertions.assertEquals(response ,user);
    }
    
}
