package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.exceptions.CityNotFoundException;
import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller

public class CityController {
    private final CityService CITY_SERVICE;

    @Autowired
    public CityController(CityService cityService) {
        this.CITY_SERVICE = cityService;
    }

    public Optional<City> getCityById(Integer id_city) {
        return CITY_SERVICE.getById(id_city);
    }

    public City addCity(City newCity) {
        return CITY_SERVICE.addCity(newCity);
    }

    public List<City> getAll() {
        return CITY_SERVICE.getAll();
    }

    public void deleteCity(City city) {
        CITY_SERVICE.delete(city);
    }

    public City update(City city, int id_city) {
        return CITY_SERVICE.update(city, id_city);
    }

    public ResponseEntity<List<City>> getCityBetween200And400() throws CityNotFoundException {
        List<City> cityList = CITY_SERVICE.getCityBetween200And400();
        ResponseEntity<List<City>> responseEntity;
        if (!cityList.isEmpty()) {
            responseEntity = ResponseEntity.ok(cityList);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CityNotFoundException();
        }
        return responseEntity;
    }
}
