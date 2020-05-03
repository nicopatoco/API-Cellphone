package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository CITY_REPOSITORY;

    public CityService(CityRepository CITY_REPOSITORY) {
        this.CITY_REPOSITORY = CITY_REPOSITORY;
    }

    public Optional<Cities> getById(Integer id_city) {
        return CITY_REPOSITORY.findById(id_city);
    }

    public void addClient(Cities newCity) {
        CITY_REPOSITORY.save(newCity);
    }


    public List<Cities> getAll() {
        return CITY_REPOSITORY.findAll();
    }

    public void delete(Cities city) {
        CITY_REPOSITORY.delete(city);
    }
}