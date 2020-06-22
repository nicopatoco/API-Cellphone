package edu.utn.TpCellphone.controller.web;

import edu.utn.TpCellphone.controller.CallController;
import edu.utn.TpCellphone.controller.LoginController;
import edu.utn.TpCellphone.dto.CallAddDto;
import edu.utn.TpCellphone.dto.LoginRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class AntennaControllerTest {
    
    @InjectMocks
    private AntennaController antennaController;
    
    @Mock
    private LoginController loginController;
    @Mock
    private CallController callController;
    
    /**
     * LOGIN
     */

    @Test
    public void loginTest() throws NoSuchAlgorithmException {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        String type = "antenna";
        ResponseEntity responseEntity = ResponseEntity.ok().build();
    
        when(loginController.login(loginRequestDto, type)).thenReturn(responseEntity);
        ResponseEntity response = antennaController.login(loginRequestDto);
    
        Assertions.assertEquals(responseEntity, response);
    }
    
    @Test
    public void logoutTest() {
        String token = "aslkdjaslkdjaskld";
        ResponseEntity responseEntity = ResponseEntity.ok(token);
        
        when(loginController.logout(token)).thenReturn(responseEntity);
        ResponseEntity response = antennaController.logout(token);
        
        Assertions.assertEquals(responseEntity, response);
    }
    
    /**
     * Call
     */
    
    @Test
    public void addCallTest() throws SQLException, URISyntaxException {
        CallAddDto call = new CallAddDto();
        URI local = new URI("localhost:8080/antenna/call/");
        ResponseEntity responseEntity = ResponseEntity.created(local).body(call);
        
        when(callController.addCall(call)).thenReturn(responseEntity);
        ResponseEntity response = antennaController.addCall(call);
        
        Assertions.assertEquals(responseEntity, response);
    }
}
