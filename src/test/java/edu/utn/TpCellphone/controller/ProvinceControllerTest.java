package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.model.Clients;
import edu.utn.TpCellphone.model.Provinces;
import edu.utn.TpCellphone.service.ClientService;
import edu.utn.TpCellphone.service.ProvinceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProvinceControllerTest {
    private ProvinceController provinceController;
    private ProvinceController provinceController2;
    private ProvinceService provinceService;
    private ProvinceService provinceService2;
    private Provinces province;

    @BeforeEach
    public void setUp() {
        this.provinceService = mock(ProvinceService.class);
        this.provinceController = new ProvinceController(provinceService);
        this.province = new Provinces(1, "Buenos Aires");
    }

    @Test
    public void testGetProvinceById() {
        when(provinceService.getById(1)).thenReturn(Optional.of(province));
        Optional<Provinces> returnedProvince = provinceController.getProvinceById(1);

        assertEquals(province, returnedProvince.get());
    }

    @Test
    public void testGetAllProvinces() {
        List<Provinces> provincesList = new ArrayList<>();
        provincesList.add(province);
        when(provinceService.getAll()).thenReturn(provincesList);
        List<Provinces> returnedList = provinceController.getAll();

        assertEquals(provincesList, returnedList);
    }

    @Test
    public void testAddProvince() {
        doNothing().when(provinceService).addProvince(province);
        provinceController.addProvince(province);

        verify(provinceService, times(1)).addProvince(province);
    }

    @Test
    public void testDeleteProvince() {
        doNothing().when(provinceService).delete(province);
        provinceController.deleteProvince(province);

        verify(provinceService, times(1)).delete(province);
    }
}