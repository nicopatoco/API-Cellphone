package edu.utn.TpCellphone.controller;


import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Cities city = new Cities(23, "posadas", 555, 4, null);
        when(service.getById(1)).thenReturn(Optional.ofNullable(city));
        Optional<Cities> response = cityController.getCityById(1);
    
        assertNotNull(response);
        assertEquals(city, response.get());
    }
    
    @Test
    public void nullGetCityByIdTest() {
        when(service.getById(1)).thenReturn(Optional.ofNullable(null));
        Optional<Cities> response = cityController.getCityById(1);
        
        assertTrue(response.isEmpty());
    }
    
    @Test
    public void addCityTest() {
        Cities city = new Cities(14, "La plata", 221, 1, null);
        when(service.addCity(city)).thenReturn(city);
        Cities response = cityController.addCity(city);
        
        assertNotNull(response);
        assertEquals(city, response);
    }
    
    @Test
    public void getAllTest() {
        List<Cities> citiesList = new ArrayList<>();
        Cities city1 = new Cities(14, "La plata", 221, 1, null);
        Cities city2 = new Cities(23, "posadas", 555, 4, null);
        citiesList.add(city1);
        citiesList.add(city2);
        
        when(service.getAll()).thenReturn(citiesList);
        List<Cities> response = cityController.getAll();
    
        assertNotNull(response);
        assertEquals(citiesList, response);
    }
    
    @Test
    public void deleteCityTest() {
        Cities city = new Cities(14, "La plata", 221, 1, null);
        doNothing().when(service).delete(city);
        cityController.deleteCity(city);
    
        verify(service, times(1)).delete(city);
    }
    
    @Test
    public void updateTest() {
        Cities updateCity = new Cities();
        updateCity.setId_province(14);
        updateCity.setName("La plata");
        updateCity.setPrefix(221);
        updateCity.setPrefix(1);
        
        when(service.update(updateCity, 1)).thenReturn(updateCity);
        Cities response = cityController.update(updateCity, 1);
        
        assertNotNull(response);
        assertEquals(updateCity, response);
    }
}
