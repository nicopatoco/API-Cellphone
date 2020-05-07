package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.Cellphones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellphoneRepository extends JpaRepository<Cellphones, Integer> {

}
