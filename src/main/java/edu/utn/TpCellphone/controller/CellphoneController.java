package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.service.CellphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cellphone")
public class CellphoneController {
    private final CellphoneService CELLPHONE_SERVICE;

    @Autowired
    public CellphoneController(CellphoneService cellphoneService) {
        this.CELLPHONE_SERVICE = cellphoneService;
    }

    @GetMapping("/{id_cellphone}")
    public Optional<Cellphone> getCellphoneById(@PathVariable Integer id_cellphone) {
        return CELLPHONE_SERVICE.getById(id_cellphone);
    }

    @PostMapping("/")
    public void addCellphone(@RequestBody Cellphone newCellphone) {
        CELLPHONE_SERVICE.addCellphone(newCellphone);
    }

    @GetMapping("/")
    public List<Cellphone> getAll() {
        return CELLPHONE_SERVICE.getAll();
    }

    @DeleteMapping("/")
    public void deleteCellphone(@RequestBody Cellphone cellphone) {
        CELLPHONE_SERVICE.delete(cellphone);
    }
}
