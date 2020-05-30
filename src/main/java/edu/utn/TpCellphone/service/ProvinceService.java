package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.exceptions.UserNotexistException;
import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.repository.ProvinceRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {

    private final ProvinceRepository PROVINCE_REPOSITORY;

    public ProvinceService(ProvinceRepository PROVINCE_REPOSITORY) {
        this.PROVINCE_REPOSITORY = PROVINCE_REPOSITORY;
    }

    public Optional<Province> getById(Integer id_province) {
        return PROVINCE_REPOSITORY.findById(id_province);
    }

    public Province addProvince(Province newProvince) {
        return PROVINCE_REPOSITORY.save(newProvince);
    }
    
    public ResponseEntity<List<Province>> getAll() {
        List<Province> provinceList = PROVINCE_REPOSITORY.findAll();
        ResponseEntity<List<Province>> responseEntity;
        if(provinceList != null) {
            responseEntity = ResponseEntity.ok(provinceList);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return responseEntity;
    }

    public void delete(Province province) {
        PROVINCE_REPOSITORY.delete(province);
    }

    public Province update(Province province, Integer id_province) {
        Province provinceToUpdate = PROVINCE_REPOSITORY.getOne(id_province);
        provinceToUpdate.setName(province.getName());
        return PROVINCE_REPOSITORY.save(provinceToUpdate);
    }
}