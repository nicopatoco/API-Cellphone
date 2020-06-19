package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.repository.PriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class PriceServiceTest {
    
    @InjectMocks
    private PriceService priceService;
    
    @Mock
    private PriceRepository repository;
    
    @Test
    public void getByIdTest() {
        Price price = new Price();
        when(repository.findById(1)).thenReturn(Optional.of(price));
        Optional<Price> response = priceService.getById(1);
        
        Assertions.assertEquals(price, response.get());
        
        when(repository.findById(1)).thenReturn(Optional.empty());
        Optional<Price> response2 = priceService.getById(1);
        
        Assertions.assertTrue(response2.isEmpty());
    }
    
    @Test
    public void getAllTest() {
        Price price = new Price(1, 7, new City(), new City());
        List<Price> prices = new ArrayList<>();
        prices.add(price);
        when(repository.findAll()).thenReturn(prices);
        List<Price> response = priceService.getAll();
        
        Assertions.assertEquals(prices, response);
    }
}
