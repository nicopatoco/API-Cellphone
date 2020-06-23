package edu.utn.TpCellphone.controller.web;

import edu.utn.TpCellphone.controller.*;
import edu.utn.TpCellphone.dto.LoginRequestDto;
import edu.utn.TpCellphone.exceptions.BillNotFoundException;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.GetBill;
import edu.utn.TpCellphone.projections.GetCall;
import edu.utn.TpCellphone.projections.GetUserTop10Destinations;
import edu.utn.TpCellphone.session.SessionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class ClientControllerTest {
    
    @InjectMocks
    ClientController clientController;
    
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
    @Mock
    private SessionManager sessionManager;
    
    /**
     * USER
     */
    
    @Test
    public void getTop10DestinationUserByIdTest() throws CallNotFoundException {
        ResponseEntity<List<GetUserTop10Destinations>> responseEntity = ResponseEntity.ok().build();
        String token = "aksjalkdjalkdjal";
        User user = new User();
        
        when(sessionManager.getCurrentUser(token)).thenReturn(user);
        when(clientController.getTop10DestinationUserById(token)).thenReturn(responseEntity);
        ResponseEntity<List<GetUserTop10Destinations>> response = clientController.getTop10DestinationUserById(token);
        
        Assertions.assertEquals(responseEntity, response);
    }
    
    @Test
    public void getCallsByRangeDateTest() throws CallNotFoundException {
        ResponseEntity<List<GetCall>> responseEntity = ResponseEntity.ok().build();
        String token = "aksjalkdjalkdjal";
        User user = new User();
        Date date = new Date();
        
        when(sessionManager.getCurrentUser(token)).thenReturn(user);
        when(clientController.getCallsByRangeDate(token, date, date)).thenReturn(responseEntity);
        ResponseEntity<List<GetCall>> response = clientController.getCallsByRangeDate(token, date, date);
        
        Assertions.assertEquals(responseEntity, response);
    }
    
    @Test
    public void getBillsByRangeDateTest() throws BillNotFoundException {
        ResponseEntity<List<GetBill>> responseEntity = ResponseEntity.ok().build();
        String token = "aksjalkdjalkdjal";
        User user = new User();
        Date date = new Date();
        
        when(sessionManager.getCurrentUser(token)).thenReturn(user);
        when(clientController.getBillsByRangeDate(token, date, date)).thenReturn(responseEntity);
        ResponseEntity<List<GetBill>> response = clientController.getBillsByRangeDate(token, date, date);
        
        Assertions.assertEquals(responseEntity, response);
    }
}
