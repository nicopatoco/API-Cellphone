package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller

public class ProvinceController {
    private final ProvinceService PROVINCE_SERVICE;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.PROVINCE_SERVICE = provinceService;
    }

    public Optional<Province> getProvinceById(Integer id_province) {
        return PROVINCE_SERVICE.getById(id_province);
    }

    public ResponseEntity<List<Province>> getAllProvince() {
        return PROVINCE_SERVICE.getAll();
    }

    public Province addProvince(Province newProvince) {
        return PROVINCE_SERVICE.addProvince(newProvince);
    }

    public void deleteProvince(Province province) {
        PROVINCE_SERVICE.delete(province);
    }

    public Province update(Province province, Integer id_province) {
        return PROVINCE_SERVICE.update(province, id_province);
    }
}
