package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Price> getPriceById(@PathVariable Integer idPrice) {
        return PRICE_SERVICE.getById(idPrice);
    }
    
    
    /**
     *     @GetMapping("/{id_city}")
     *     public Optional<City> getCityById(@PathVariable Integer id_city) {
     *         return CITY_SERVICE.getById(id_city);
     *     }
     *
     *     @PostMapping("/")
     *     public City addCity(@RequestBody City newCity) {
     *         return CITY_SERVICE.addCity(newCity);
     *     }
     *
     *     @GetMapping("/")
     *     public List<City> getAll() {
     *         return CITY_SERVICE.getAll();
     *     }
     *
     *     @DeleteMapping("/")
     *     public void deleteCity(@RequestBody City city) {
     *         CITY_SERVICE.delete(city);
     *     }
     *
     *     @PutMapping("/{id_city}")
     *     public City update(@RequestBody City city, @PathVariable int id_city) {
     *         return CITY_SERVICE.update(city, id_city);
     *     }
     */
    
    
}
