package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.repository.CityRepository;
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

    private Cities cities;

    @BeforeEach
    public void setUo() {
        this.cities = new Cities(1, 1, "mar del plata", 223);
    }

    @Test
    public void getByIdTest() {
        when(repository.findById(1)).thenReturn(java.util.Optional.of(cities));
        Optional<Cities> response = cityService.getById(1);

        assertNotNull(response);

        assertEquals(1, cities.getId_city());
        assertEquals("mar del plata", cities.getName());
    }

    @Test
    public void addClientTest() {
        when(repository.save(cities)).thenReturn(cities);
        cityService.addClient(cities);

        verify(repository, times(1)).save(cities);
    }

    @Test
    public void getAllTest() {
        List<Cities> citiesList = new ArrayList<>();
        citiesList.add(cities);
        when(repository.findAll()).thenReturn(citiesList);
        List<Cities> response = cityService.getAll();

        assertNotNull(response);
        assertEquals(citiesList, response);
    }

    @Test
    public void deleteTest() {
        doNothing().when(repository).delete(cities);
        cityService.delete(cities);

        verify(repository, times(1)).delete(cities);
    }

}
