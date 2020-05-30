package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.repository.CityRepository;
import edu.utn.TpCellphone.repository.ProvinceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    
    private final CityRepository CITY_REPOSITORY;
    private final ProvinceRepository PROVINCE_REPOSITORY;
    
    public CityService(CityRepository CITY_REPOSITORY, ProvinceRepository province_repository) {
        this.CITY_REPOSITORY = CITY_REPOSITORY;
        this.PROVINCE_REPOSITORY = province_repository;
    }
    
    public Optional<City> getById(Integer id_city) {
        return CITY_REPOSITORY.findById(id_city);
    }
    
    public City addCity(City newCity) {
        Optional<Province> province = PROVINCE_REPOSITORY.findById(newCity.getProvince().getIdProvince());
        newCity.setProvince(province.get());
        return CITY_REPOSITORY.save(newCity);
    }
    
    
    public List<City> getAll() {
        return CITY_REPOSITORY.findAll();
    }
    
    public void delete(City city) {
        CITY_REPOSITORY.delete(city);
    }
    
    public City update(City city, int id_city) {
        City cityToUpdate = CITY_REPOSITORY.getOne(id_city);
        cityToUpdate.setName(city.getName());
        cityToUpdate.setPrefix(city.getPrefix());
        cityToUpdate.setIdCity(city.getIdCity());
        return CITY_REPOSITORY.save(cityToUpdate);
    }
}