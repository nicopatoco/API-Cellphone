package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.exceptions.CellphoneNotFoundException;
import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.service.CellphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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
    
    public ResponseEntity<Cellphone> downLine(int idCellphone) throws CellphoneNotFoundException {
        ResponseEntity<Cellphone> responseEntity;
        Optional<Cellphone> cellphone = CELLPHONE_SERVICE.downLine(idCellphone);
        if(!cellphone.isEmpty()){
            responseEntity = ResponseEntity.ok(cellphone.get());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CellphoneNotFoundException();
        }
        return responseEntity;
    }
    
    public ResponseEntity upLine(int idCellphone) throws CellphoneNotFoundException {
        ResponseEntity<Cellphone> responseEntity;
        Optional<Cellphone> cellphone = CELLPHONE_SERVICE.upLine(idCellphone);
        if(!cellphone.isEmpty()){
            responseEntity = ResponseEntity.ok(cellphone.get());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CellphoneNotFoundException();
        }
        return responseEntity;
    }
}
