package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Bill;
import edu.utn.TpCellphone.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final BillRepository BILL_REPOSITORY;

    public BillService(BillRepository BILL_REPOSITORY) {
        this.BILL_REPOSITORY = BILL_REPOSITORY;
    }

    public List<Bill> getAllBills() {
        return BILL_REPOSITORY.findAll();
    }

    public Optional<Bill> getByIdBill(Integer idBill) {
        return BILL_REPOSITORY.findById(idBill);
    }
}
