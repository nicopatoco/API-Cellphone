package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Cellphones;
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


    public Optional<Cellphones> getById(Integer id_cellphone) {
        return CELLPHONE_REPOSITORY.findById(id_cellphone);
    }

    public void addClient(Cellphones newCellphone) {
        CELLPHONE_REPOSITORY.save(newCellphone);
    }

    public List<Cellphones> getAll() {
        return CELLPHONE_REPOSITORY.findAll();
    }

    public void delete(Cellphones cellphone) {
        CELLPHONE_REPOSITORY.delete(cellphone);
    }
}
