package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.repository.CityRepository;
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
    
    @Mock
    private CityRepository cityRepository;

    @Test
    public void addClientTest() {
        User user = new User();
        user.setIdUser(1);
        user.setId("35885684");
        user.setName("nicolas");
        user.setName("surname");
        user.setCity(new City(1, "mar del plata", 223, new Province()));
        when(repository.save(user)).thenReturn(user);
        when(cityRepository.findById(user.getCity().getIdCity())).thenReturn(Optional.of(new City()));
        User response = userService.addUser(user);

        assertNotNull(response);
        assertEquals(user, response);
    }

    @Test
    public void getByIdTest() {
        User user = new User(1,"123","juan","Perez", "juancioto", "123abc", null, null);
        when(repository.findById(5)).thenReturn(java.util.Optional.ofNullable(user));
        Optional<User> response = userService.getById(5);
    
        assertNotNull(response);
        assertEquals(user, response.get());
    }

    @Test
    public void getAllTest() {
        User user1 = new User(1,"123","juan","Perez", "juancioto", "123abc", null, null);
        User user2 = new User(1,"333","nico","herrera", "nicopatoco", "123abc", null, null);
        List<User> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);
        when(repository.findAll()).thenReturn(usersList);
        List<User> response = userService.getAll(null);
    
        assertNotNull(response);
        assertEquals(usersList, response);
    }

    @Test
    public void getAllTest2() {
        User user = new User(1,"123","juan","Perez", "juancioto", "123abc", null, null );
        List<User> usersList = new ArrayList<>();
        usersList.add(user);
        when(repository.findByName("juan")).thenReturn(usersList);
        List<User> response = userService.getAll("juan");
    
        assertNotNull(response);
        assertEquals(usersList, response);
    }
    
    @Test
    public void deleteTest() {
        User user = new User(1,"123","juan","Perez", "juancioto", "123abc", null, null);
        doNothing().when(repository).delete(user);
        userService.delete(user);

        verify(repository, times(1)).delete(user);
    }
    
    @Test
    public void updateTest() {
        User user = new User(1,"333","nico","herrera", "nicopatoco", "123abc", null, new City());
        User userToUpdate = new User(1,"123","juan","Perez", "juancioto", "123abc", null, new City());
        int idToUpdate = 1;
        
        when(repository.getOne(idToUpdate)).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        User response = userService.update(userToUpdate, idToUpdate);
    
        Assertions.assertEquals(response ,user);
    }
    
}
