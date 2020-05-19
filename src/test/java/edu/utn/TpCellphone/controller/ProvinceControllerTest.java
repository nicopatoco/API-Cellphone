package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Provinces;
import edu.utn.TpCellphone.service.ProvinceService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class ProvinceControllerTest {
    @InjectMocks
    private ProvinceController provinceController;

    @Mock
    private ProvinceService service;

    private Provinces province;

    @BeforeEach
    public void setUp() {
        this.province = new Provinces(1, "Buenos Aires");
    }

    @Test
    public void getProvinceByIdTest() {
        when(service.getById(1)).thenReturn(Optional.of(province));
        Optional<Provinces> response = provinceController.getProvinceById(1);

        assertEquals(province, response.get());
        assertEquals(province.getName(), response.get().getName());
        assertEquals(province.getId_province(), response.get().getId_province());
        assertEquals(province.toString(), response.get().toString());
    }

    @Test
    public void getAllProvincesTest() {
        List<Provinces> provincesList = new ArrayList<>();
        provincesList.add(province);
        when(service.getAll()).thenReturn(provincesList);
        List<Provinces> responseList = provinceController.getAllProvince();

        assertNotNull(responseList);
        assertEquals(provincesList, responseList);
    }

    @Test
    public void addProvinceTest() {
        when(service.addProvince(province)).thenReturn(province);
        Provinces response = provinceController.addProvince(province);

        assertNotNull(response);
        assertEquals(province, response);
    }

    @Test
    public void deleteProvinceTest() {
        doNothing().when(service).delete(province);
        provinceController.deleteProvince(province);

        verify(service, times(1)).delete(province);
    }

    @Test
    public void updateTest() {
        Provinces updateProvince = new Provinces();
        updateProvince.setId_province(1);
        updateProvince.setName("Mar del plata");

        when(service.update(updateProvince, 1)).thenReturn(updateProvince);
        Provinces response = provinceController.update(updateProvince, 1);

        assertNotNull(response);
        assertEquals(updateProvince, response);
    }
}