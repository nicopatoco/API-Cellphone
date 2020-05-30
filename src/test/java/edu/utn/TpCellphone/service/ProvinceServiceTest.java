package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.repository.ProvinceRepository;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class ProvinceServiceTest {
    @InjectMocks
    private ProvinceService provinceService;

    @Mock
    private ProvinceRepository repository;

    private Province province;

    @BeforeEach
    public void setUp() {
        this.province = new Province(1, "Buenos Aires");
    }

    @Test
    public void addProvinceTest() {
        when(repository.save(province)).thenReturn(province);
        Province response = provinceService.addProvince(province);

        assertNotNull(response);
        assertEquals(province, response);
    }

    @Test
    public void getByIdTest() {
        when(repository.findById(1)).thenReturn(Optional.ofNullable(province));
        Optional<Province> response = provinceService.getById(1);

        assertNotNull(response);
        assertEquals(province, response.get());

        //Testing null case
        when(repository.findById(1)).thenReturn(Optional.ofNullable(null));
        Optional<Province> response1 = provinceService.getById(1);

        assertTrue(response1.isEmpty());
        assertEquals(province, response.get());
    }

    @Test
    public void getAllTest() {
        List<Province> provincesList = new ArrayList<>();
        provincesList.add(province);
        when(repository.findAll()).thenReturn(provincesList);
        List<Province> responseList = provinceService.getAll();

        assertEquals(provincesList, responseList);
    }

    @Test
    public void deleteTest() {
        doNothing().when(repository).delete(province);
        provinceService.delete(province);

        verify(repository, times(1)).delete(province);
    }

    @Test
    public void updateTest() {
        Province provinceToUpdate = new Province();
        provinceToUpdate.setName("Cordoba");
        int idToUpdate = 1;
        
        when(repository.getOne(idToUpdate)).thenReturn(province);
        when(repository.save(province)).thenReturn(province);
        Province response = provinceService.update(provinceToUpdate, idToUpdate);
        
        assertEquals(response ,province);
    }
}
