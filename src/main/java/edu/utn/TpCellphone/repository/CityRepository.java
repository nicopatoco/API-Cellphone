package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<Cities, Integer> {
}
