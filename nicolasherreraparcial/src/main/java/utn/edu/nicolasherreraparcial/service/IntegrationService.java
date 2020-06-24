package utn.edu.nicolasherreraparcial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.edu.nicolasherreraparcial.dto.LoginRequestDto;
import utn.edu.nicolasherreraparcial.model.City;
import utn.edu.nicolasherreraparcial.service.integration.IntegrationComponent;

import java.util.List;

@Service
public class IntegrationService {
    
    @Autowired
    IntegrationComponent integrationComponent;
    
    public ResponseEntity getLoggedUserFromApiRest(LoginRequestDto loginRequestDto) {
        return integrationComponent.loginClient(loginRequestDto);
    }
    
    public ResponseEntity<List<City>> getCityBetween200And400(){
        return integrationComponent.getCityBetween200And400();
    }
    
}

