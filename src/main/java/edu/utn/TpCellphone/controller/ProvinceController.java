package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Provinces;
import edu.utn.TpCellphone.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {
    private final ProvinceService PROVINCE_SERVICE;
  
    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.PROVINCE_SERVICE = provinceService;
    }
  
    @GetMapping("/{id_province}")
    public Optional<Provinces> getProvinceById(@PathVariable Integer id_province) {
        return PROVINCE_SERVICE.getById(id_province);
    }
  
    @GetMapping("/")
    public List<Provinces> getAllProvince() {
        return PROVINCE_SERVICE.getAll();
    }
  
    @PostMapping("/")
    public Provinces addProvince(@RequestBody Provinces newProvince) {
        return PROVINCE_SERVICE.addProvince(newProvince);
    }
  
    @DeleteMapping("/")
    public void deleteProvince(@RequestBody Provinces province) {
        PROVINCE_SERVICE.delete(province);
    }
  
    @PutMapping("/{id_province}")
    public Provinces update(@RequestBody Provinces provinces, @PathVariable Integer id_province) {
        return PROVINCE_SERVICE.update(provinces, id_province);
    }
}
