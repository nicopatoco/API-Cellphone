package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.repository.CityRepository;
import edu.utn.TpCellphone.repository.ProvinceRepository;
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
    
    @Mock
    private ProvinceRepository provinceRepository;
    
    private City city;
    
    @BeforeEach
    public void setUo() {
        this.city = new City(1, "mar del plata", 223, new Province(1, "Buenos Aires"));
    }
    
    @Test
    public void getByIdTest() {
        when(repository.findById(1)).thenReturn(java.util.Optional.of(city));
        Optional<City> response = cityService.getById(1);
        
        assertNotNull(response);
        assertEquals(1, city.getIdCity());
    }
    
    @Test
    public void addCityTest() {
        Province province = new Province(1, "Buenos Aires");
        when(repository.save(city)).thenReturn(city);
        when(provinceRepository.findById(city.getProvince().getIdProvince())).thenReturn(Optional.of(province));
        City response = cityService.addCity(city);
        
        Assertions.assertNotNull(response);
        Assertions.assertEquals(city, response);
    }
    
    @Test
    public void getAllTest() {
        List<City> citiesList = new ArrayList<>();
        citiesList.add(city);
        when(repository.findAll()).thenReturn(citiesList);
        List<City> response = cityService.getAll();
        
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
        City cityToUpdate = new City();
        cityToUpdate.setName("Pinamar");
        cityToUpdate.setPrefix(225);
        int idToUpdate = 1;
        
        when(repository.getOne(idToUpdate)).thenReturn(city);
        when(repository.save(city)).thenReturn(city);
        City response = cityService.update(cityToUpdate, idToUpdate);
        
        Assertions.assertEquals(response ,city);
    }
    
    /**
     * Test city service, this method should return a list of cities between 200 and 400 prefix.
     */
    @Test
    public void cityBetween200and400Test() {
        List<City> cityList = new ArrayList<>();
        City city1 = new City(1,"mar del plata", 223, null);
        City city2 = new City(1,"mar chiquita", 221, null);
        City city3 = new City(1,"mar de las pampas", 222, null);
        cityList.add(city1);
        cityList.add(city2);
        cityList.add(city3);
        when(repository.getCityBetween200And400()).thenReturn(cityList);
        List<City> response = cityService.getCityBetween200And400();
        
        assertNotNull(response);
        assertEquals(cityList, response);
    }
}
