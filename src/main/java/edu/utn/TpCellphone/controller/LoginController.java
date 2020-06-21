package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.dto.LoginRequestDto;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {

    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public LoginController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }


    public ResponseEntity login(LoginRequestDto loginRequestDto, String type) throws NoSuchAlgorithmException {
        ResponseEntity response;
        User u = userController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword(), type);
        String token = sessionManager.createSession(u);
        System.out.println("MY TOKEN !: " + token);
        response = ResponseEntity.ok().headers(createHeaders(token)).build();
        return response;

    }


    public ResponseEntity logout(String token) {
        sessionManager.removeSession(token);
        return ResponseEntity.ok().build();
    }


}
