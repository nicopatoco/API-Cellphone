package edu.utn.TpCellphone.controller.web;

import edu.utn.TpCellphone.controller.*;
import edu.utn.TpCellphone.dto.LoginRequestDto;
import edu.utn.TpCellphone.exceptions.BillNotFoundException;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.exceptions.PriceNotFoundException;
import edu.utn.TpCellphone.model.Bill;
import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.GetCall;
import edu.utn.TpCellphone.projections.GetUserReduce;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class AdminControllerTest {
    
    @InjectMocks
    private AdminController adminController;
    
    @Mock
    private LoginController loginController;
    @Mock
    private UserController userController;
    @Mock
    private CellphoneController cellphoneController;
    @Mock
    private PriceController priceController;
    @Mock
    private BillController billController;
    
    /**
     * LOGIN
     */
    
    @Test
    public void loginTest() throws NoSuchAlgorithmException {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        String type = "admin";
        ResponseEntity responseEntity = ResponseEntity.ok().build();
        
        when(loginController.login(loginRequestDto, type)).thenReturn(responseEntity);
        ResponseEntity response = adminController.login(loginRequestDto);
        
        Assertions.assertEquals(responseEntity, response);
    }
    
    @Test
    public void logoutTest() {
        String token = "aslkdjaslkdjaskld";
        ResponseEntity responseEntity = ResponseEntity.ok(token);
        
        when(loginController.logout(token)).thenReturn(responseEntity);
        ResponseEntity response = adminController.logout(token);
        
        Assertions.assertEquals(responseEntity, response);
    }
    
    /**
     * USERS
     */
    
    @Test
    public void addUserTest() {
        ResponseEntity<GetUserReduce> responseEntity = ResponseEntity.ok().build();
        User user = new User();
        
        when(userController.addUser(user)).thenReturn(responseEntity);
        ResponseEntity<GetUserReduce> response = adminController.addUser(user);
        
        Assertions.assertEquals(responseEntity, response);
    }
    
    @Test
    public void getAllTest() {
        List<User> users = new ArrayList<>();
        User user = new User();
        users.add(user);
        
        when(userController.getAll("nico")).thenReturn(users);
        List<User> response = adminController.getAll("nico");
        
        Assertions.assertEquals(users, response);
    }
    
    @Test
    public void getUserByIdTest() {
        User user = new User();
        
        when(userController.getUserById(1)).thenReturn(Optional.of(user));
        Optional<User> response = adminController.getUserById(1);
        
        assertEquals(user, response.get());
    }
    
    @Test
    public void deleteUserTest() {
        User user = new User();
        
        doNothing().when(userController).deleteUser(user);
        adminController.deleteUser(user);
        
        verify(userController, times(1)).deleteUser(user);
    }
    
    @Test
    public void updateTest() throws NoSuchAlgorithmException {
        User user = new User();
        
        when(userController.update(user, 1)).thenReturn(user);
        User response = adminController.update(user, 1);
        
        assertEquals(user, response);
    }
    
    @Test
    public void getCallsByUserIdTest() throws CallNotFoundException {
        ResponseEntity<List<GetCall>> responseEntity = ResponseEntity.ok().build();
        
        when(userController.getCallsByUserId(1)).thenReturn(responseEntity);
        ResponseEntity<List<GetCall>> response = adminController.getCallsByUserId(1);
        
        assertEquals(responseEntity, response);
    }
    
    /**
     * CELLPHONE
     */
    
    @Test
    public void addCellphoneTest() {
        Cellphone cellphone = new Cellphone();
        
        doNothing().when(cellphoneController).addCellphone(cellphone);
        adminController.addCellphone(cellphone);
        
        verify(cellphoneController, times(1)).addCellphone(cellphone);
    }
    
    @Test
    public void getAllCellphoneTest() {
        List<Cellphone> cellphoneList = new ArrayList<>();
        Cellphone cellphone = new Cellphone();
        cellphoneList.add(cellphone);
    
        when(cellphoneController.getAll()).thenReturn(cellphoneList);
        List<Cellphone> response = adminController.getAll();
    
        Assertions.assertEquals(cellphoneList, response);
    }
    
    @Test
    public void getCellphoneByIdTest() {
        Cellphone cellphone = new Cellphone();
        
        when(cellphoneController.getCellphoneById(1)).thenReturn(Optional.of(cellphone));
        Optional<Cellphone> response = adminController.getCellphoneById(1);
        
        assertEquals(cellphone, response.get());
    }
    
    @Test
    public void deleteCellphoneTest() {
        Cellphone cellphone = new Cellphone();
    
        doNothing().when(cellphoneController).deleteCellphone(cellphone);
        adminController.deleteCellphone(cellphone);
    
        verify(cellphoneController, times(1)).deleteCellphone(cellphone);
    }
    
    /**
     * PRICES
     */
    
    @Test
    public void getAllPricesTest() throws PriceNotFoundException {
        ResponseEntity<List<Price>> responseEntity = ResponseEntity.ok().build();
        
        when(priceController.getAllPrices()).thenReturn(responseEntity);
        ResponseEntity<List<Price>> response = adminController.getAllPrices();
        
        assertEquals(responseEntity, response);
        assertEquals(200, response.getStatusCodeValue());
    }
    
    @Test
    public void getPriceByIdTest() throws PriceNotFoundException {
        ResponseEntity<Price> responseEntity = ResponseEntity.ok().build();
        
        when(priceController.getPriceById(1)).thenReturn(responseEntity);
        ResponseEntity<Price> response = adminController.getPriceById(1);
        
        assertEquals(responseEntity, response);
        assertEquals(200, response.getStatusCodeValue());
    }
    
    /**
     * BILL
     */

    @Test
    public void getAllBillsTest() throws BillNotFoundException {
        ResponseEntity<List<Bill>> responseEntity = ResponseEntity.ok().build();
    
        when(billController.getAllBills()).thenReturn(responseEntity);
        ResponseEntity<List<Bill>> response = adminController.getAllBills();
    
        assertEquals(responseEntity, response);
        assertEquals(200, response.getStatusCodeValue());
    }
    
    @Test
    public void getByIdBillTest() throws BillNotFoundException {
        ResponseEntity<Bill> responseEntity = ResponseEntity.ok().build();
        
        when(billController.getByIdBill(1)).thenReturn(responseEntity);
        ResponseEntity<Bill> response = adminController.getByIdBill(1);
        
        assertEquals(responseEntity, response);
        assertEquals(200, response.getStatusCodeValue());
    }
}