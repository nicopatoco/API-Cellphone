package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.service.CellphoneService;
import org.junit.jupiter.api.Assertions;
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
public class CellphoneControllerTest {
    
    @InjectMocks
    private CellphoneController cellphoneController;
    
    @Mock
    private CellphoneService service;
    
    @Test
    public void getCellphoneByIdTest() {
        Cellphone cellphone = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());
        when(service.getById(cellphone.getIdCellphone())).thenReturn(java.util.Optional.of(cellphone));
        Optional<Cellphone> response = cellphoneController.getCellphoneById(cellphone.getIdCellphone());
        
        Assertions.assertNotNull(response);
        Assertions.assertEquals(cellphone, response.get());
        
        when(service.getById(cellphone.getIdCellphone())).thenReturn(Optional.empty());
        Optional<Cellphone> response2 = cellphoneController.getCellphoneById(cellphone.getIdCellphone());
        
        assertTrue(response2.isEmpty());
        assertTrue(!cellphone.toString().isEmpty());
    }
    
    @Test
    public void addCellphoneTest() {
        Cellphone cellphone = new Cellphone();
        cellphone.setIdCellphone(1);
        cellphone.setCellphoneNumber("2233123680");
        cellphone.setLineType(Cellphone.LineType.home);
        cellphone.setUser(new User());
        
        doNothing().when(service).addCellphone(cellphone);
        cellphoneController.addCellphone(cellphone);
        
        verify(service, times(1)).addCellphone(cellphone);
    }
    
    @Test
    public void getAllTest() {
        Cellphone cellphone1 = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());
        Cellphone cellphone2 = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());
        List<Cellphone> cellphoneList = new ArrayList<>();
        cellphoneList.add(cellphone1);
        cellphoneList.add(cellphone2);
        
        when(service.getAll()).thenReturn(cellphoneList);
        List<Cellphone> response = cellphoneController.getAll();
        
        assertEquals(cellphone1, cellphone2);
        assertEquals(cellphoneList, response);
        assertNotNull(cellphoneList);
    }
    
    @Test
    public void deleteCellphoneTest() {
        Cellphone cellphone1 = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());
        
        doNothing().when(service).delete(cellphone1);
        cellphoneController.deleteCellphone(cellphone1);
        
        verify(service, times(1)).delete(cellphone1);
    }
}
