package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.exceptions.BillNotFoundException;
import edu.utn.TpCellphone.exceptions.CityNotFoundException;
import edu.utn.TpCellphone.exceptions.PriceNotFoundException;
import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.service.PriceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class PriceControllerTest {
    
    @InjectMocks
    private PriceController priceController;
    
    @Mock
    private PriceService priceService;
    
    @Test
    public void getPriceByIdTest() throws PriceNotFoundException {
        Price price = new Price(1, 20, new City(), new City());
    
        when(priceService.getById(price.getIdPrice())).thenReturn(java.util.Optional.of(price));
        ResponseEntity<Price> response = priceController.getPriceById(price.getIdPrice());
    
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(!price.toString().isEmpty());
        
        when(priceService.getById(price.getIdPrice())).thenReturn(Optional.empty());
    
        assertThrows(PriceNotFoundException.class, () -> {
            priceController.getPriceById(price.getIdPrice());
        });

        Assertions.assertEquals("Price not found", new PriceNotFoundException().getMessage());
    }
    
    @Test
    public void getAllPricesTest() throws PriceNotFoundException {
        Price price1 = new Price(1, 20, new City(), new City());
        Price price2 = new Price();
        price2.setCityDestination(new City());
        price2.setCityOrigin(new City());
        price2.setIdPrice(1);
        price2.setPricePerMinute(10);
        List<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
    
        when(priceService.getAll()).thenReturn(prices);
        ResponseEntity<List<Price>> response = priceController.getAllPrices();
    
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(prices, response.getBody());
    
        when(priceService.getAll()).thenReturn(new ArrayList<>());
    
        assertThrows(PriceNotFoundException.class, () -> {
            priceController.getAllPrices();
        });
    }
}
