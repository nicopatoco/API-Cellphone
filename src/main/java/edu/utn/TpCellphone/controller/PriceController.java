package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.exceptions.PriceNotFoundException;
import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/price")
public class PriceController {
    private final PriceService PRICE_SERVICE;
    
    @Autowired
    public PriceController(PriceService priceService) {
        this.PRICE_SERVICE = priceService;
    }
    
    @GetMapping("/{idPrice}")
    public ResponseEntity<Price> getPriceById(@PathVariable Integer idPrice) throws PriceNotFoundException {
        ResponseEntity<Price> responseEntity;
        Optional<Price> price = PRICE_SERVICE.getById(idPrice);
        if (!price.isEmpty()) {
            responseEntity = ResponseEntity.ok(price.get());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new PriceNotFoundException();
        }
        return responseEntity;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Price>> getAllProvince() throws PriceNotFoundException {
        ResponseEntity<List<Price>> responseEntity;
        List<Price> price = PRICE_SERVICE.getAll();
        if (!price.isEmpty()) {
            responseEntity = ResponseEntity.ok(price);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new PriceNotFoundException();
        }
        return responseEntity;
    }
}
