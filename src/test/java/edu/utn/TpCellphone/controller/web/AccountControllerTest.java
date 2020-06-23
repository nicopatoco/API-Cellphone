package edu.utn.TpCellphone.controller.web;

import edu.utn.TpCellphone.controller.LoginController;
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

import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class AccountControllerTest {
    
    @InjectMocks
    private AccountController accountController;
    
    @Mock
    private LoginController loginController;
    
    @Test
    public void loginAdminTest() throws NoSuchAlgorithmException {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        String type = "admin";
        ResponseEntity responseEntity = ResponseEntity.ok().build();
        
        when(loginController.login(loginRequestDto, type)).thenReturn(responseEntity);
        ResponseEntity response = accountController.loginAdmin(loginRequestDto);
        
        Assertions.assertEquals(responseEntity, response);
    }
    
    @Test
    public void loginClientTest() throws NoSuchAlgorithmException {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        String type = "client";
        ResponseEntity responseEntity = ResponseEntity.ok().build();
        
        when(loginController.login(loginRequestDto, type)).thenReturn(responseEntity);
        ResponseEntity response = accountController.loginClient(loginRequestDto);
        
        Assertions.assertEquals(responseEntity, response);
    }
    
    @Test
    public void loginAntennaTest() throws NoSuchAlgorithmException {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        String type = "antenna";
        ResponseEntity responseEntity = ResponseEntity.ok().build();
        
        when(loginController.login(loginRequestDto, type)).thenReturn(responseEntity);
        ResponseEntity response = accountController.loginAntenna(loginRequestDto);
        
        Assertions.assertEquals(responseEntity, response);
    }
    
    @Test
    public void logoutTest() {
        String token = "aslkdjaslkdjaskld";
        ResponseEntity responseEntity = ResponseEntity.ok(token);
        
        when(loginController.logout(token)).thenReturn(responseEntity);
        ResponseEntity response = accountController.logout(token);
        
        Assertions.assertEquals(responseEntity, response);
    }
}
