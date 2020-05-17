package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Provinces;
import edu.utn.TpCellphone.repository.ProvinceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProvinceServiceTest {
    private ProvinceService provinceService;
    private ProvinceRepository provinceRepository;
    private Provinces province;

    @BeforeEach
    public void setUp() {
        this.provinceRepository = mock(ProvinceRepository.class);
        this.provinceService = new ProvinceService(provinceRepository);
        this.province = new Provinces(1, "Buenos Aires");
    }

    @Test
    public void addProvinceTest() {
        when(provinceRepository.save(province)).thenReturn(province);
        provinceService.addProvince(province);

        verify(provinceRepository, times(1)).save(province);
    }

    @Test
    public void getByIdTest() {
        when(provinceRepository.findById(1)).thenReturn(Optional.ofNullable(province));
        Optional<Provinces> returnedProvince = provinceService.getById(1);

        assertEquals(province, returnedProvince.get());
    }

    @Test
    public void getAllTest() {
        List<Provinces> provincesList = new ArrayList<>();
        provincesList.add(province);
        when(provinceRepository.findAll()).thenReturn(provincesList);
        List<Provinces> returnedProvince = provinceService.getAll();

        assertEquals(provincesList, returnedProvince);
    }

    @Test
    public void deleteTest() {
        doNothing().when(provinceRepository).delete(province);
        provinceService.delete(province);

        verify(provinceRepository, times(1)).delete(province);
    }
}
