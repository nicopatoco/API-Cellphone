package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.dto.CallDto;
import edu.utn.TpCellphone.exceptions.BillNotFoundException;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.model.*;
import edu.utn.TpCellphone.service.BillService;
import edu.utn.TpCellphone.service.CallService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class BillControllerTest {
    
    @InjectMocks
    private BillController billController;
    
    @Mock
    private BillService service;
    
    @Test
    public void getByIdBillsTest() throws BillNotFoundException {
        Bill bill = new Bill(1, 25, 600.00, new Date(), new Date(), new User(), new Cellphone());
    
        when(service.getByIdBill(bill.getIdBill())).thenReturn(java.util.Optional.of(bill));
        ResponseEntity<Bill> response = billController.getByIdBill(bill.getIdBill());
    
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bill.getAmountOfCalls(), response.getBody().getAmountOfCalls());
    
        when(service.getByIdBill(bill.getIdBill())).thenReturn(Optional.empty());
    
        Assertions.assertThrows(BillNotFoundException.class, () -> {
            billController.getByIdBill(bill.getIdBill());
        });
    }
    
    @Test
    public void getAllBillsTest() throws BillNotFoundException {
        Bill bill1 = new Bill(1, 25, 600.00, new Date(), new Date(), new User(), new Cellphone());
        Bill bill2 = new Bill();
        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);
        
        when(service.getAllBills()).thenReturn(bills);
        ResponseEntity<List<Bill>> response = billController.getAllBills();
        
        assertEquals(200, response.getStatusCodeValue());
        
        when(service.getAllBills()).thenReturn(new ArrayList<>());
        
        Assertions.assertThrows(BillNotFoundException.class, () -> {
            billController.getAllBills();
        });
    }
}
