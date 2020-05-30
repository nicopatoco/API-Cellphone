package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.Cellphone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellphoneRepository extends JpaRepository<Cellphone, Integer> {

}
