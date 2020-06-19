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
}
