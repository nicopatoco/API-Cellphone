package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Provinces;
import edu.utn.TpCellphone.repository.ProvinceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {

    private final ProvinceRepository PROVINCE_REPOSITORY;

    public ProvinceService(ProvinceRepository PROVINCE_REPOSITORY) {
        this.PROVINCE_REPOSITORY = PROVINCE_REPOSITORY;
    }

    public Optional<Provinces> getById(Integer id_province) {
        return PROVINCE_REPOSITORY.findById(id_province);
    }

    public Provinces addProvince(Provinces newProvince) {
        return PROVINCE_REPOSITORY.save(newProvince);
    }

    public List<Provinces> getAll() {
        return PROVINCE_REPOSITORY.findAll();
    }

    public void delete(Provinces province) {
        PROVINCE_REPOSITORY.delete(province);
    }

    public Provinces update(Provinces provinces, Integer id_province) {
        Provinces provincesToUpdate = PROVINCE_REPOSITORY.getOne(id_province);
        provincesToUpdate.setName(provinces.getName());
        return PROVINCE_REPOSITORY.save(provincesToUpdate);
    }
}