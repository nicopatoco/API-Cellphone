package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.dto.CallDto;
import edu.utn.TpCellphone.exceptions.BillNotFoundException;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.model.Bill;
import edu.utn.TpCellphone.model.Call;
import edu.utn.TpCellphone.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BillController {

    private final BillService BILL_SERVICE;

    @Autowired
    public BillController(BillService BILL_SERVICE) {
        this.BILL_SERVICE = BILL_SERVICE;
    }

    public ResponseEntity<List<Bill>> getAllBills() throws BillNotFoundException {
        List<Bill> bills = BILL_SERVICE.getAllBills();

        ResponseEntity<List<Bill>> responseEntity;
        if (!bills.isEmpty()) {
            responseEntity = ResponseEntity.ok(bills);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new BillNotFoundException();
        }
        return responseEntity;
    }

    public ResponseEntity<Bill> getByIdBill(Integer idBill) throws BillNotFoundException {
        Optional<Bill> bill = BILL_SERVICE.getByIdBill(idBill);

        ResponseEntity<Bill> responseEntity;
        if (!bill.isEmpty()) {
            responseEntity = ResponseEntity.ok(bill.get());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new BillNotFoundException();
        }
        return responseEntity;
    }

}
