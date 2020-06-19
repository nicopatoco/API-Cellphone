package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.exceptions.BillNotFoundException;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.GetBill;
import edu.utn.TpCellphone.projections.GetCall;
import edu.utn.TpCellphone.projections.GetUserReduce;
import edu.utn.TpCellphone.projections.GetUserTop10Destinations;
import edu.utn.TpCellphone.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static java.util.Optional.ofNullable;
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
    
    @Mock
    private List<GetUserTop10Destinations> calls;
    
    @Mock()
    private List<GetCall> getCallProjections;
    
    @Mock()
    private List<GetBill> getBillProjections;
    
    @Test
    public void getUserByIdTest() {
        User user = new User(1, "123", "juan", "Perez", "juancioto", "123abc", null, null);
        when(service.getById(1)).thenReturn(ofNullable(user));
        Optional<User> response = userController.getUserById(1);
        
        assertNotNull(response);
        assertEquals(user, response.get());
    }
    
    @Test
    public void nullGetUserByIdTest() {
        when(service.getById(1)).thenReturn(ofNullable(null));
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
        assertTrue(!user1.toString().isEmpty());
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
    
    @Test()
    public void getUserReduceTest() {
        when(service.getReduceUser(1)).thenReturn(userReduce);
        ResponseEntity<GetUserReduce> response = userController.getUserReduce(1);
        
        assertEquals(userReduce, response);
    }
    
    @Test
    public void topTenCallsTest() throws CallNotFoundException {
        User user = new User(1, "35885684", "Nicolas", "Herrera", "nicopatoco", "acb123", User.UserType.client, new City());
        when(service.getTop10DestinationUserById(user.getIdUser())).thenReturn(this.calls);
        ResponseEntity<List<GetUserTop10Destinations>> response = userController.getTop10DestinationUserById(user.getIdUser());
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(this.calls, response.getBody());
        
        List<GetUserTop10Destinations> list = new ArrayList<>();
        when(service.getTop10DestinationUserById(user.getIdUser())).thenReturn(list);
        
        assertThrows(CallNotFoundException.class, () -> {
            userController.getTop10DestinationUserById(user.getIdUser());
        });
    }
    
    @Test
    public void getCallsByRangeDateTest() throws CallNotFoundException {
        Date date1 = new Date();
        Date date2 = new Date();
        date1.setTime(1111111111);
        date2.setTime(1222222222);
        when(service.getCallsByRangeDate(1, date1, date2)).thenReturn(getCallProjections);
        ResponseEntity<List<GetCall>> response = userController.getCallsByRangeDate(1, date1, date2);
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(this.getCallProjections, response.getBody());
        
        List<GetCall> getCalls = new ArrayList<>();
        when(service.getCallsByRangeDate(1, new Date(), new Date())).thenReturn(getCalls);
    
        assertThrows(CallNotFoundException.class, () -> {
            userController.getCallsByRangeDate(1, new Date(), new Date());
        });
    }
    
    @Test
    public void getBillsByRangeDateTest() throws BillNotFoundException {
        Date date1 = new Date();
        Date date2 = new Date();
        date1.setTime(1111111111);
        date2.setTime(1222222222);
        when(service.getBillsByRangeDate(1, date1, date2)).thenReturn(getBillProjections);
        ResponseEntity<List<GetBill>> response = userController.getBillsByRangeDate(1, date1, date2);
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(this.getBillProjections, response.getBody());
        
        List<GetBill> getBills = new ArrayList<>();
        when(service.getBillsByRangeDate(1, new Date(), new Date())).thenReturn(getBills);
        
        assertThrows(BillNotFoundException.class, () -> {
            userController.getBillsByRangeDate(1, new Date(), new Date());
        });
    }
}
