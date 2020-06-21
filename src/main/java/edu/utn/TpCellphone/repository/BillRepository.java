package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.Bill;
import edu.utn.TpCellphone.model.City;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {


}
