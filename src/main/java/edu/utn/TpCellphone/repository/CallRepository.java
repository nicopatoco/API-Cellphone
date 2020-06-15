package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRepository extends JpaRepository<Call, Integer> {
}
