package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Province;
import edu.utn.TpCellphone.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/province")
public class ProvinceController {
    private final ProvinceService PROVINCE_SERVICE;
  
    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.PROVINCE_SERVICE = provinceService;
    }
  
    @GetMapping("/{id_province}")
    public Optional<Province> getProvinceById(@PathVariable Integer id_province) {
        return PROVINCE_SERVICE.getById(id_province);
    }
    
    @GetMapping("/")
    public List<Province> getAllProvince() {
        return PROVINCE_SERVICE.getAll();
    }
  
    @PostMapping("/")
    public Province addProvince(@RequestBody Province newProvince) {
        return PROVINCE_SERVICE.addProvince(newProvince);
    }
  
    @DeleteMapping("/")
    public void deleteProvince(@RequestBody Province province) {
        PROVINCE_SERVICE.delete(province);
    }
  
    @PutMapping("/{id_province}")
    public Province update(@RequestBody Province province, @PathVariable Integer id_province) {
        return PROVINCE_SERVICE.update(province, id_province);
    }
}
