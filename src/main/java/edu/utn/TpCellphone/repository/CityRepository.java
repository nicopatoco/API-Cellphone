package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.City;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    
    @Query(value = "select * from cities where prefix > 200 and prefix < 400", nativeQuery = true)
    List<City> getCityBetween200And400();

}
