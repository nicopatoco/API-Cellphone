package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.dto.LoginRequestDto;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.session.SessionManager;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class LoginControllerTest {
    
    @InjectMocks
    private LoginController loginController;
    
    @Mock
    private UserController userController;
    @Mock
    private SessionManager sessionManager;
    
    @Test
    public void loginTest() throws NoSuchAlgorithmException {
        User user = new User();
        String username = "nicopatoco";
        String password = "acb123";
        String type = "client";
        String token = "jkashdkjahdkjsajhd";
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername(username);
        loginRequestDto.setPassword(password);
        LoginRequestDto loginRequestDto2 = new LoginRequestDto();
        loginRequestDto2.setUsername(username);
        loginRequestDto2.setPassword(password);
        
        when(userController.login(username, password, type)).thenReturn(user);
        when(sessionManager.createSession(user)).thenReturn(token);
        
        ResponseEntity response = loginController.login(loginRequestDto, type);
        
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(token, response.getHeaders().get("Authorization").get(0));
        Assertions.assertEquals(username, loginRequestDto.getUsername());
        Assertions.assertEquals(password, loginRequestDto.getPassword());
        Assertions.assertEquals(loginRequestDto2.toString(), loginRequestDto.toString());
        Assertions.assertEquals(loginRequestDto2.hashCode(), loginRequestDto.hashCode());
    }
    
    @Test
    public void logoutTest() {
        String token = "jkashdkjahdkjsajhd";
        doNothing().when(sessionManager).removeSession(token);
        ResponseEntity response = loginController.logout(token);
        
        verify(sessionManager, times(1)).removeSession(token);
    }
}
