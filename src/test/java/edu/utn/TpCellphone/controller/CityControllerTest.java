package edu.utn.TpCellphone.controller;


import edu.utn.TpCellphone.exceptions.CityNotFoundException;
import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class CityControllerTest {
    @InjectMocks
    private CityController cityController;
    
    @Mock
    private CityService service;
    
    @Test
    public void getCityByIdTest() {
        City city = new City(23, "posadas", 555, new Province());
        when(service.getById(1)).thenReturn(Optional.ofNullable(city));
        Optional<City> response = cityController.getCityById(1);
    
        assertNotNull(response);
        assertEquals(city, response.get());
    }
    
    @Test
    public void nullGetCityByIdTest() {
        when(service.getById(1)).thenReturn(Optional.ofNullable(null));
        Optional<City> response = cityController.getCityById(1);
        
        assertTrue(response.isEmpty());
    }
    
    @Test
    public void addCityTest() {
        City city = new City(14, "La plata", 221, new Province());
        when(service.addCity(city)).thenReturn(city);
        City response = cityController.addCity(city);
        
        assertNotNull(response);
        assertEquals(city, response);
    }
    
    @Test
    public void getAllTest() {
        List<City> citiesList = new ArrayList<>();
        City city1 = new City(14, "La plata", 221, new Province());
        City city2 = new City(23, "posadas", 555, new Province());
        citiesList.add(city1);
        citiesList.add(city2);
        
        when(service.getAll()).thenReturn(citiesList);
        List<City> response = cityController.getAll();
    
        assertNotNull(response);
        assertEquals(citiesList, response);
    }
    
    @Test
    public void deleteCityTest() {
        City city = new City(14, "La plata", 221, new Province());
        doNothing().when(service).delete(city);
        cityController.deleteCity(city);
    
        verify(service, times(1)).delete(city);
    }
    
    @Test
    public void updateTest() {
        City updateCity = new City();
        updateCity.setName("La plata");
        updateCity.setPrefix(221);
        updateCity.setIdCity(1);
        updateCity.setProvince(new Province(1, "Buenos Aires"));
        
        when(service.update(updateCity, 1)).thenReturn(updateCity);
        City response = cityController.update(updateCity, 1);
        
        assertNotNull(response);
        assertEquals(updateCity, response);
    }
    
    /**
     * Test city service, this method should return a list of cities between 200 and 400 prefix.
     * inside a response entity
     * status code 200
     */
    @Test
    public void cityBetween200and400Test() throws CityNotFoundException {
        List<City> cityList = new ArrayList<>();
        City city1 = new City(1,"mar del plata", 223, null);
        City city2 = new City(1,"mar chiquita", 221, null);
        City city3 = new City(1,"mar de las pampas", 222, null);
        cityList.add(city1);
        cityList.add(city2);
        cityList.add(city3);
        
        when(service.getCityBetween200And400()).thenReturn(cityList);
        ResponseEntity<List<City>> response = cityController.getCityBetween200And400();
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cityList, response.getBody());
        assertTrue(!city1.toString().isEmpty());
    }
    
    /**
     * Testing the exception case, and status code 400
     */
    @Test
    public void wrongCityBetween200and400Test() throws CityNotFoundException {
        List<City> cityList = new ArrayList<>();
        
        when(service.getCityBetween200And400()).thenReturn(cityList);
        
        assertThrows(CityNotFoundException.class, () -> {
            ResponseEntity<List<City>> response = cityController.getCityBetween200And400();
            assertEquals(400, response);
        });
    }
}
