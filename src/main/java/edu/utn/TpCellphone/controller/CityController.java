package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService CITY_SERVICE;

    @Autowired
    public CityController(CityService cityService) {
        this.CITY_SERVICE = cityService;
    }

    @GetMapping("/{id_city}")
    public Optional<Cities> getCityById(@PathVariable Integer id_city) {
        return CITY_SERVICE.getById(id_city);
    }

    @PostMapping("/")
    public Cities addCity(@RequestBody Cities newCity) {
        return CITY_SERVICE.addCity(newCity);
    }

    @GetMapping("/")
    public List<Cities> getAll() {
        return CITY_SERVICE.getAll();
    }

    @DeleteMapping("/")
    public void deleteCity(@RequestBody Cities city) {
        CITY_SERVICE.delete(city);
    }
    
    @PutMapping("/{id_city}")
    public Cities update(@RequestBody Cities city, @PathVariable int id_city) {
        return CITY_SERVICE.update(city, id_city);
    }
}
