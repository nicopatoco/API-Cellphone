package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.repository.PriceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PriceService {
    private final PriceRepository PRICE_REPOSITORY;
    
    public PriceService(PriceRepository PRICE_REPOSITORY) {
        this.PRICE_REPOSITORY = PRICE_REPOSITORY;
    }
    
    public ResponseEntity<Price> getById(Integer idPrice) {
        ResponseEntity<Price> responseEntity;
        Optional<Price> price = PRICE_REPOSITORY.findById(idPrice);
        if(!price.isEmpty()){
            responseEntity = ResponseEntity.ok(price.get());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new RuntimeException("User not found");
        }
        return responseEntity;
    }
}
