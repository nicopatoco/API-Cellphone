package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.dto.CallAddDto;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.exceptions.CellphoneUnavailableException;
import edu.utn.TpCellphone.model.Bill;
import edu.utn.TpCellphone.model.Call;
import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.repository.CallRepository;
import edu.utn.TpCellphone.repository.CellphoneRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class CallServiceTest {
    
    @InjectMocks
    private CallService callService;
    
    @Mock
    private CallRepository repository;
    
    @Mock
    private CellphoneService cellphoneService;

    @Mock
    private CellphoneService cellphoneService2;

    @Test
    public void getCallByIdTest() {
        Call call = new Call();
        call.setIdCall(1);
        call.setStartTime(new Date());
        call.setEndTime(new Date());
        call.setFinalValue(100.00);
        call.setNumberOrigin("2233123680");
        call.setNumberDestination("2233123679");
        call.setDuration(5);
        call.setCellphoneOrigin(new Cellphone());
        call.setCellphoneDestination(new Cellphone());
        call.setPrice(new Price());
        call.setBill(new Bill());
        
        when(repository.findById(call.getIdCall())).thenReturn(java.util.Optional.of(call));
        Optional<Call> response = callService.getById(call.getIdCall());
        
        assertEquals(call, response.get());
        assertEquals(call.toString(), response.get().toString());
        
        when(repository.findById(call.getIdCall())).thenReturn(Optional.empty());
        Optional<Call> response2 = callService.getById(call.getIdCall());
        
        assertTrue(response2.isEmpty());
    }
    
    @Test
    public void getAllCallsTest() throws CallNotFoundException {
        Call call = new Call(1, new Date(), new Date(), 123.0, "2233123680", "2233123679", 5, new Cellphone(), new Cellphone(), new Price(), new Bill());
        List<Call> calls = new ArrayList<>();
        calls.add(call);
        
        when(repository.findAll()).thenReturn(calls);
        List<Call> response = callService.getAll();
        
        assertEquals(calls, response);
    }
    
    @Test
    public void addCallTest() throws SQLException, CellphoneUnavailableException {
        CallAddDto callDto = new CallAddDto("2233123679", "2233123680", new Date(), new Date());
        
        doNothing().when(repository).addCall(callDto.getNumberOrigin(), callDto.getNumberDestination(), callDto.getStartTime(), callDto.getEndTime());
        when(cellphoneService.isAvailable("2233123679")).thenReturn(false);
        when(cellphoneService.isAvailable("2233123680")).thenReturn(false);

        Assertions.assertThrows(CellphoneUnavailableException.class, () -> {
            callService.addCall(callDto);
        });
    }
}
