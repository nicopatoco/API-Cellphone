package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.dto.CallAddDto;
import edu.utn.TpCellphone.dto.CallDto;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.exceptions.CellphoneUnavailableException;
import edu.utn.TpCellphone.model.Bill;
import edu.utn.TpCellphone.model.Call;
import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.service.CallService;
import edu.utn.TpCellphone.service.CellphoneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class CallControllerTest {
    
    @InjectMocks
    private CallController callController;
    
    @Mock
    private CallService service;
    
    @Mock
    private CellphoneService cellphoneService;
    
    
    @Test
    public void getCallByIdTest() throws CallNotFoundException {
        CallDto callDto = new CallDto("2233123680", "2233123679", 5);
        Call call = new Call(1, new Date(), new Date(), 123.0, "2233123680", "2233123679", 5, new Cellphone(), new Cellphone(), new Price(), new Bill());
        
        when(service.getById(call.getIdCall())).thenReturn(java.util.Optional.of(call));
        ResponseEntity<CallDto> response = callController.getCallById(call.getIdCall());
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(callDto.getNumberDestination(), response.getBody().getNumberDestination());
        
        when(service.getById(call.getIdCall())).thenReturn(Optional.empty());
        
        Assertions.assertThrows(CallNotFoundException.class, () -> {
            callController.getCallById(call.getIdCall());
        });

        Assertions.assertEquals("Call not found", new CallNotFoundException().getMessage());
    }
    
    @Test
    public void getAllCallsTest() throws CallNotFoundException {
        Call call = new Call();
        List<Call> calls = new ArrayList<>();
        calls.add(call);
        
        when(service.getAll()).thenReturn(calls);
        ResponseEntity<List<CallDto>> response = callController.getAllCalls();
        
        assertEquals(200, response.getStatusCodeValue());
        
        when(service.getAll()).thenReturn(new ArrayList<>());
        
        Assertions.assertThrows(CallNotFoundException.class, () -> {
            callController.getAllCalls();
        });

        Assertions.assertEquals("Call not found", new CallNotFoundException().getMessage());
    }
    
    @Test
    public void addCallTest() throws SQLException, CellphoneUnavailableException {
        CallAddDto callDto = new CallAddDto("2233123679", "2233123680", new Date(), new Date());
        
        doNothing().when(service).addCall(callDto);
        when(cellphoneService.isAvailable("2233123679")).thenReturn(true);
        when(cellphoneService.isAvailable("2233123680")).thenReturn(true);
        
        ResponseEntity response = callController.addCall(callDto);
        
        verify(service, times(1)).addCall(callDto);
        assertEquals(201, response.getStatusCodeValue());
        
        doThrow(SQLException.class)
                .when(service)
                .addCall(callDto);
        
        ResponseEntity response2 = callController.addCall(callDto);
        assertEquals(500, response2.getStatusCodeValue());

        Assertions.assertEquals("The cellphone have a downLine", new CellphoneUnavailableException().getMessage());
    }
}
