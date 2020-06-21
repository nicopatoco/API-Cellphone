package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.GetBill;
import edu.utn.TpCellphone.projections.GetCall;
import edu.utn.TpCellphone.projections.GetUserReduce;
import edu.utn.TpCellphone.projections.GetUserTop10Destinations;
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
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    
    @Mock
    private ResponseEntity<GetUserReduce> userReduce;
    
    @Mock
    private UserService mockService;
    
    @Mock
    private GetUserReduce getUserReduce;
    
    @Mock
    private List<GetUserTop10Destinations> getUserTop10Destinations;
    
    @Mock
    private List<GetCall> getCalls;
    
    @Mock
    private List<GetBill> getBills;
    
    @Test
    public void addUserTest() {
        User user = new User();
        user.setIdUser(1);
        user.setId("35885684");
        user.setName("nicolas");
        user.setName("surname");
        user.setCity(new City(1, "mar del plata", 223, new Province()));
    
        when(cityRepository.findById(user.getCity().getIdCity())).thenReturn(Optional.of(new City()));
        when(repository.save(user)).thenReturn(user);
        when(mockService.getReduceUser(user.getIdUser())).thenReturn(userReduce);
        when(repository.getUserById(user.getIdUser())).thenReturn(getUserReduce);
        ResponseEntity<GetUserReduce> response = userService.addUser(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(getUserReduce, response.getBody());
    }
    
    @Test
    public void BadRequestaddUserTest() {
        User user = new User();
        user.setIdUser(1);
        user.setId("35885684");
        user.setName("nicolas");
        user.setName("surname");
        user.setCity(new City(1, "mar del plata", 223, new Province()));
        
        when(cityRepository.findById(user.getCity().getIdCity())).thenReturn(Optional.of(new City()));
        when(repository.save(user)).thenReturn(user);
        when(mockService.getReduceUser(user.getIdUser())).thenReturn(userReduce);
        when(repository.getUserById(user.getIdUser())).thenReturn(null);
        ResponseEntity<GetUserReduce> response = userService.addUser(user);
        
        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
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
    public void updateTest() throws NoSuchAlgorithmException {
        User user = new User(1,"333","nico","herrera", "nicopatoco", "123abc", null, new City());
        User userToUpdate = new User(1,"123","juan","Perez", "juancioto", "123abc", null, new City());
        int idToUpdate = 1;
        
        when(repository.getOne(idToUpdate)).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        User response = userService.update(userToUpdate, idToUpdate);
    
        Assertions.assertEquals(response ,user);
    }
    
    @Test
    public void loginTest() throws NoSuchAlgorithmException {

        User user = new User();
        String username = "nicopatoco";
        String password = "abc123";
        String type = "admin";
        user.setUsername(username);
        user.setPassword(password);
        when(repository.userExists(username, userService.hashPass(password), type)).thenReturn(user);
        User response = userService.login(username, password, type);
        
        assertNotNull(response);
        assertEquals(user, response);
    }
    
    @Test
    public void exceptionLoginTest() {
        User user = new User();
        String username = "nicopatoco";
        String password = "abc123";
        String type = "admin";
        user.setUsername(username);
        user.setPassword(password);
        when(repository.userExists(username, password, type)).thenReturn(null);
        
        assertThrows(RuntimeException.class, () -> {
            userService.login(username, password, type);
        });
    }
    
    @Test
    public void getTop10DestinationUserByIdTest() {
        when(repository.getTop10DestinationUserById(1)).thenReturn(getUserTop10Destinations);
        List<GetUserTop10Destinations> response = userService.getTop10DestinationUserById(1);
        
        assertEquals(getUserTop10Destinations, response);
    }
    
    @Test
    public void getCallsByRangeDateTest() {
        Date date1 = new Date();
        Date date2 = new Date();
        date1.setTime(1111111111);
        date2.setTime(1222222222);
        when(repository.getCallsByRangeDate(1,date1, date2)).thenReturn(getCalls);
        List<GetCall> response = userService.getCallsByRangeDate(1,date1, date2);
        
        assertEquals(getCalls, response);
    }
    
    @Test
    public void getBillsByRangeDateTest() {
        Date date1 = new Date();
        Date date2 = new Date();
        date1.setTime(1111111111);
        date2.setTime(1222222222);
        when(repository.getBillsByRangeDate(1,date1, date2)).thenReturn(getBills);
        List<GetBill> response = userService.getBillsByRangeDate(1,date1, date2);
        
        assertEquals(getBills, response);
    }
}
