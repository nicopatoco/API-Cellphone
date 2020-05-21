package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class CityServiceTest {
    @InjectMocks
    private CityService cityService;
    
    @Mock
    private CityRepository repository;
    
    private Cities city;
    
    @BeforeEach
    public void setUo() {
        this.city = new Cities(1, "mar del plata", 223, 1, null);
    }
    
    @Test
    public void getByIdTest() {
        when(repository.findById(1)).thenReturn(java.util.Optional.of(city));
        Optional<Cities> response = cityService.getById(1);
        
        assertNotNull(response);
        assertEquals(1, city.getId_city());
    }
    
    @Test
    public void addClientTest() {
        when(repository.save(city)).thenReturn(city);
        Cities response = cityService.addCity(city);
        
        Assertions.assertNotNull(response);
        Assertions.assertEquals(city, response);
    }
    
    @Test
    public void getAllTest() {
        List<Cities> citiesList = new ArrayList<>();
        citiesList.add(city);
        when(repository.findAll()).thenReturn(citiesList);
        List<Cities> response = cityService.getAll();
        
        assertNotNull(response);
        assertEquals(citiesList, response);
    }
    
    @Test
    public void deleteTest() {
        doNothing().when(repository).delete(city);
        cityService.delete(city);
        
        verify(repository, times(1)).delete(city);
    }
    
    @Test
    public void updateTest() {
        Cities cityToUpdate = new Cities();
        cityToUpdate.setName("Pinamar");
        cityToUpdate.setPrefix(225);
        cityToUpdate.setId_province(1);
        int idToUpdate = 1;
        when(repository.getOne(idToUpdate)).thenReturn(city);
        when(repository.save(city)).thenReturn(city);
        Cities response = cityService.update(cityToUpdate, idToUpdate);
        
        Assertions.assertEquals(response ,city);
    }
}
