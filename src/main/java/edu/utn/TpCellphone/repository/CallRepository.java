package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface CallRepository extends JpaRepository<Call, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "insert into calls (number_origin, number_destination, start_time, end_time)" +
                    " values(:number_origin, :number_destination, :start_time, :end_time);",
            nativeQuery = true)
    void addCall(@Param("number_origin") String numberOrigin,
                 @Param("number_destination") String numberDestination,
                 @Param("start_time") Date startTime,
                 @Param("end_time") Date endTime);
}
