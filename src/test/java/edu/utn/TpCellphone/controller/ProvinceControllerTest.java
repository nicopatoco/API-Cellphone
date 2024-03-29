package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.service.ProvinceService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
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
    
    private Province province;
    
    @BeforeEach
    public void setUp() {
        this.province = new Province(1, "Buenos Aires");
    }
    
    @Test
    public void getProvinceByIdTest() {
        when(service.getById(1)).thenReturn(Optional.of(province));
        Optional<Province> response = provinceController.getProvinceById(1);
        
        assertEquals(province, response.get());
        assertEquals(province.getName(), response.get().getName());
        assertEquals(province.getIdProvince(), response.get().getIdProvince());
        assertEquals(province.toString(), response.get().toString());
    }
    
    @Test
    public void getAllProvincesTest() {
        List<Province> provincesList = new ArrayList<>();
        provincesList.add(province);
        ResponseEntity<List<Province>> responseEntity;
        responseEntity = ResponseEntity.ok(provincesList);
        when(service.getAll()).thenReturn(responseEntity);
        ResponseEntity<List<Province>> responseList = provinceController.getAllProvince();
        
        Assert.assertEquals(200, responseList.getStatusCodeValue());
        Assert.assertEquals(provincesList, responseList.getBody());
    }
    
    @Test
    public void addProvinceTest() {
        when(service.addProvince(province)).thenReturn(province);
        Province response = provinceController.addProvince(province);
        
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
        Province updateProvince = new Province();
        updateProvince.setIdProvince(1);
        updateProvince.setName("Mar del plata");
        
        when(service.update(updateProvince, 1)).thenReturn(updateProvince);
        Province response = provinceController.update(updateProvince, 1);
        
        assertNotNull(response);
        assertEquals(updateProvince, response);
    }
}