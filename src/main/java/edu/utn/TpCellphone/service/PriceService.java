package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PriceService {
    private final PriceRepository PRICE_REPOSITORY;
    
    public PriceService(PriceRepository PRICE_REPOSITORY) {
        this.PRICE_REPOSITORY = PRICE_REPOSITORY;
    }
    
    public Optional<Price> getById(Integer idPrice) {
        return PRICE_REPOSITORY.findById(idPrice);
    }
}
