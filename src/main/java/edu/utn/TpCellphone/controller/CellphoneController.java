package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Cellphones;
import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.service.CellphoneService;
import edu.utn.TpCellphone.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cellphones")
public class CellphoneController {
    private final CellphoneService CELLPHONE_SERVICE;

    @Autowired
    public CellphoneController(CellphoneService cellphoneService) {
        this.CELLPHONE_SERVICE = cellphoneService;
    }

    @GetMapping("/{id_cellphone}")
    public Optional<Cellphones> getCellphoneById(@PathVariable Integer id_cellphone) {
        return CELLPHONE_SERVICE.getById(id_cellphone);
    }

    @PostMapping("/")
    public void addCellphone(@RequestBody Cellphones newCellphone) {
        CELLPHONE_SERVICE.addClient(newCellphone);
    }

    @GetMapping("/")
    public List<Cellphones> getAll() {
        return CELLPHONE_SERVICE.getAll();
    }

    @DeleteMapping("/")
    public void deleteCellphone(@RequestBody Cellphones cellphone) {
        CELLPHONE_SERVICE.delete(cellphone);
    }
}
