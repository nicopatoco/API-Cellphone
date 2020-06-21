package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.service.CellphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller

public class CellphoneController {
    private final CellphoneService CELLPHONE_SERVICE;

    @Autowired
    public CellphoneController(CellphoneService cellphoneService) {
        this.CELLPHONE_SERVICE = cellphoneService;
    }

    public Optional<Cellphone> getCellphoneById(Integer id_cellphone) {
        return CELLPHONE_SERVICE.getById(id_cellphone);
    }


    public void addCellphone(Cellphone newCellphone) {
        CELLPHONE_SERVICE.addCellphone(newCellphone);
    }

    public List<Cellphone> getAll() {
        return CELLPHONE_SERVICE.getAll();
    }

    public void deleteCellphone(Cellphone cellphone) {
        CELLPHONE_SERVICE.delete(cellphone);
    }
}
