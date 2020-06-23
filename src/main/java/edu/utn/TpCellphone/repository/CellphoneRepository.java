package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.Cellphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CellphoneRepository extends JpaRepository<Cellphone, Integer> {
    
    @Query(value = "select * from cellphones where cellphone_number = ? and status = 1")
    Optional<Cellphone> isAvailable(String cellphone);
}
