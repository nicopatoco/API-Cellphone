package edu.utn.TpCellphone.controller.web;

import edu.utn.TpCellphone.controller.UserController;
import edu.utn.TpCellphone.dto.LoginRequestDto;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {
    
    UserController userController;
    SessionManager sessionManager;
    
    @Autowired
    public LoginController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }
    
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) {
        ResponseEntity response;
        User u = userController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        String token = sessionManager.createSession(u);
        System.out.println("MY TOKEN !: "+ token );
        response = ResponseEntity.ok().headers(createHeaders(token)).build();
        return response;
    }
    
    
    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        sessionManager.removeSession(token);
        return ResponseEntity.ok().build();
    }
    
    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }
}
