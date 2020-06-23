package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.repository.CellphoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CellphoneService {
    private final CellphoneRepository CELLPHONE_REPOSITORY;

    public CellphoneService(CellphoneRepository CELLPHONE_REPOSITORY) {
        this.CELLPHONE_REPOSITORY = CELLPHONE_REPOSITORY;
    }


    public Optional<Cellphone> getById(Integer id_cellphone) {
        return CELLPHONE_REPOSITORY.findById(id_cellphone);
    }

    public void addCellphone(Cellphone newCellphone) {
        CELLPHONE_REPOSITORY.save(newCellphone);
    }

    public List<Cellphone> getAll() {
        return CELLPHONE_REPOSITORY.findAll();
    }

    public void delete(Cellphone cellphone) {
        CELLPHONE_REPOSITORY.delete(cellphone);
    }
    
    public boolean isAvailable(String cellphone) {
        Optional<Cellphone> cellphone1 = CELLPHONE_REPOSITORY.isAvailable(cellphone);
        return !cellphone1.isEmpty();
    }
    
    public Optional<Cellphone> downLine(int idCellphone) {
        Optional<Cellphone> cellphone = CELLPHONE_REPOSITORY.findById(idCellphone);
        if(!cellphone.isEmpty()){
            cellphone.get().setStatus(false);
            CELLPHONE_REPOSITORY.save(cellphone.get());
        }
        return cellphone;
    }
    
    public Optional<Cellphone> upLine(int idCellphone) {
        Optional<Cellphone> cellphone = CELLPHONE_REPOSITORY.findById(idCellphone);
        if(!cellphone.isEmpty()){
            cellphone.get().setStatus(true);
            CELLPHONE_REPOSITORY.save(cellphone.get());
        }
        return cellphone;
    }
}
