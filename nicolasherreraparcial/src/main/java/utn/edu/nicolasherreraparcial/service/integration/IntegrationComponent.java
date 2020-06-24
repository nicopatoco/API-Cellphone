package utn.edu.nicolasherreraparcial.service.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import utn.edu.nicolasherreraparcial.dto.LoginRequestDto;
import utn.edu.nicolasherreraparcial.model.City;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class IntegrationComponent {
    
    private RestTemplate rest;
    private static String urlLogin = "http://localhost:8080/account/client";
    private static String urlCall = "http://localhost:8080/client/getCityBetween200And400";
    
    @PostConstruct
    private void init() {
        rest = new RestTemplateBuilder()
                .build();
    }
    
    
    public ResponseEntity loginClient(LoginRequestDto loginRequestDto) {
        return rest.postForEntity(urlLogin, loginRequestDto, LoginRequestDto.class);
    }
    
    public List<City> getCityBetween200And400() {
        return rest.getForObject(urlCall, List<Call>);
    }
}
