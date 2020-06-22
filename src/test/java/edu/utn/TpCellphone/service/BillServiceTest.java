package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Bill;
import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.repository.BillRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class BillServiceTest {
    
    @InjectMocks
    private BillService billService;
    
    @Mock
    private BillRepository repository;
    
    /*
     public List<Bill> getAllBills() {
        return BILL_REPOSITORY.findAll();
    }

    public Optional<Bill> getByIdBill(Integer idBill) {
        return BILL_REPOSITORY.findById(idBill);
    }
     */
    @Test
    public void getAllBills() {
        Bill bill1 = new Bill(1, 25, 600.00, new Date(), new Date(), new User(), new Cellphone());
        Bill bill2 = new Bill();
        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);
    
        when(repository.findAll()).thenReturn(bills);
        List<Bill> response = billService.getAllBills();
    
        assertEquals(bills, response);
    }
    
    @Test
    public void getByBills() {
        Bill bill = new Bill();
        bill.setIdBill(1);
        bill.setAmountOfCalls(25);
        bill.setFinalPrice(600.00);
        bill.setBillDate(new Date());
        bill.setDueDate(new Date());
        bill.setUser(new User());
        bill.setCellphone(new Cellphone());
    
        when(repository.findById(bill.getIdBill())).thenReturn(Optional.of(bill));
        Optional<Bill> response = billService.getByIdBill(bill.getIdBill());
        
        assertEquals(bill, response.get());
        assertEquals(bill.getAmountOfCalls(), response.get().getAmountOfCalls());
        assertEquals(bill.getIdBill(), response.get().getIdBill());
        assertEquals(bill.getBillDate(), response.get().getBillDate());
        assertEquals(bill.getDueDate(), response.get().getDueDate());
        assertEquals(bill.getCellphone(), response.get().getCellphone());
        assertTrue(bill.getFinalPrice() == response.get().getFinalPrice());
        assertEquals(bill.getUser(), response.get().getUser());
        assertEquals(bill.toString(), response.get().toString());
    }
}
