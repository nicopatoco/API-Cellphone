package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.repository.CellphoneRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class CellphoneServiceTest {
    
    @InjectMocks
    private CellphoneService cellphoneService;
    
    @Mock
    private CellphoneRepository repository;
    
    @Test
    public void getByIdTest() {
        Cellphone cellphone = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());
        
        when(repository.findById(cellphone.getIdCellphone())).thenReturn(java.util.Optional.of(cellphone));
        Optional<Cellphone> response = cellphoneService.getById(cellphone.getIdCellphone());
        
        assertEquals(cellphone, response.get());
        assertNotNull(response);
    }
    
    @Test
    public void addCellphoneTest() {
        Cellphone cellphone = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());
        when(repository.save(cellphone)).thenReturn(cellphone);
        cellphoneService.addCellphone(cellphone);
        
        verify(repository, times(1)).save(cellphone);
    }
    
    @Test
    public void getAllTest() {
        Cellphone cellphone1 = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());
        Cellphone cellphone2 = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());
        List<Cellphone> cellphoneList = new ArrayList<>();
        cellphoneList.add(cellphone1);
        cellphoneList.add(cellphone2);
        
        when(repository.findAll()).thenReturn(cellphoneList);
        List<Cellphone> response = cellphoneService.getAll();
        
        assertNotNull(response);
        assertEquals(cellphoneList, response);
    }
    
    @Test
    public void deleteTest() {
        Cellphone cellphone1 = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());
        
        doNothing().when(repository).delete(cellphone1);
        cellphoneService.delete(cellphone1);
        
        verify(repository, times(1)).delete(cellphone1);
    }

    @Test
    public void isAvailableTest(){

        Cellphone cellphone1 = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());

        when(repository.isAvailable(cellphone1.getCellphoneNumber())).thenReturn(Optional.of(cellphone1));
        Boolean response = cellphoneService.isAvailable(cellphone1.getCellphoneNumber());

        Assertions.assertTrue(response);

        when(repository.isAvailable(cellphone1.getCellphoneNumber())).thenReturn(Optional.empty());
        Boolean response2 = cellphoneService.isAvailable(cellphone1.getCellphoneNumber());

        Assertions.assertFalse(response2);
    }

     @Test
    public void downLineTest(){
         Cellphone cellphone1 = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());

         when(repository.findById(cellphone1.getIdCellphone())).thenReturn(Optional.empty());
         Optional<Cellphone> response = cellphoneService.downLine(cellphone1.getIdCellphone());

         Assertions.assertTrue(cellphone1.getStatus());

         when(repository.findById(cellphone1.getIdCellphone())).thenReturn(Optional.of(cellphone1));
         Optional<Cellphone> response2 = cellphoneService.downLine(cellphone1.getIdCellphone());

         Assertions.assertFalse(cellphone1.getStatus());
     }

     @Test
     public void upLineTest(){
         Cellphone cellphone1 = new Cellphone(1, "2233123680", Cellphone.LineType.home, true, new User());

         when(repository.findById(cellphone1.getIdCellphone())).thenReturn(Optional.empty());
         Optional<Cellphone> response = cellphoneService.upLine(cellphone1.getIdCellphone());

         Assertions.assertTrue(cellphone1.getStatus());

         when(repository.findById(cellphone1.getIdCellphone())).thenReturn(Optional.of(cellphone1));
         Optional<Cellphone> response2 = cellphoneService.upLine(cellphone1.getIdCellphone());

         Assertions.assertTrue(cellphone1.getStatus());

     }
}
