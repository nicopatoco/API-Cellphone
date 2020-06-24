package utn.edu.nicolasherreraparcial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.nicolasherreraparcial.dto.LoginRequestDto;
import utn.edu.nicolasherreraparcial.excpetions.CityNotFoundException;
import utn.edu.nicolasherreraparcial.model.City;
import utn.edu.nicolasherreraparcial.model.User;
import utn.edu.nicolasherreraparcial.service.IntegrationService;
import utn.edu.nicolasherreraparcial.session.Session;
import utn.edu.nicolasherreraparcial.session.SessionManager;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequestMapping("/account")
public class Controller {
    
    @Autowired
    IntegrationService integrationService;
    private final SessionManager sessionManager;
    
    String token;
    
    public Controller(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    
    @PostMapping("/client/")
    public ResponseEntity loginClient(@RequestBody LoginRequestDto loginRequestDto) throws NoSuchAlgorithmException {
        ResponseEntity responseEntity = integrationService.getLoggedUserFromApiRest(loginRequestDto);
        return responseEntity;
    }
    
    /**
     * PARCIAL NICOLAS HERRERA 1
     */
    @GetMapping("/user/getCityBetween200And400")
    public ResponseEntity<List<City>> getCityBetween200And400(@RequestHeader("Authorization") String sessionToken) throws CityNotFoundException {
        User user = this.sessionManager.getCurrentUser(sessionToken);
        return integrationService.getCityBetween200And400();
    }
    
}
