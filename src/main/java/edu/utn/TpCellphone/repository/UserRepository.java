package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "Select * from users where name = ?1", nativeQuery = true)
    List<User> findByName(String name);

    @Query(value = "select * from users where username = ?1 and password = ?2 and type = ?3", nativeQuery = true)
    User userExists(String username, String password, String type);

    @Query(value = "select u.name as name, u.surname, c.name as City from users u join cities c on c.id_city = u.id_city where u.id_user = ?1", nativeQuery = true)
    GetUserReduce getUserById(int idUser);

    @Query(value = "SELECT ca.number_destination as numberDestination, COUNT(number_destination) as counter FROM users as u " +
            "       JOIN cellphones as ce ON u.id_user = ce.id_user " +
            "       JOIN calls as ca ON ca.id_cellphone_origin = ce.id_cellphone " +
            "       WHERE u.id_user = ?1 GROUP BY ca.number_destination ORDER BY number_destination DESC LIMIT 10", nativeQuery = true)
    List<GetUserTop10Destinations> getTop10DestinationUserById(Integer idClient);

    @Query(value = "SELECT ca.number_origin as numberOrigin, ca.number_destination as numberDestination, ca.duration as duration FROM users as u " +
            "       JOIN cellphones as ce ON u.id_user = ce.id_user" +
            "       JOIN calls as ca ON ca.id_cellphone_origin = ce.id_cellphone" +
            "       WHERE u.id_user = ?1 AND (ca.end_time BETWEEN ?2 AND ?3)", nativeQuery = true)
    List<GetCall> getCallsByRangeDate(Integer idClient, Date dateFrom, Date dateTo);


    @Query(value = "SELECT b.id_bill as id, c.cellphone_number as cellphone, b.final_price as total, b.due_date as expiration FROM users as u " +
            "       JOIN Bills as b on b.id_user = u.id_user " +
            "       JOIN cellphones c on b.id_cellphone = c.id_cellphone" +
            "       WHERE u.id_user = ?1 AND (b.bill_date BETWEEN ?2 AND ?3)", nativeQuery = true)
    List<GetBill> getBillsByRangeDate(Integer idClient, Date dateFrom, Date dateTo);

    @Query(value = "SELECT ca.number_origin as numberOrigin, ca.number_destination as numberDestination, ca.duration as duration FROM users as u " +
            "       JOIN cellphones as ce ON u.id_user = ce.id_user" +
            "       JOIN calls as ca ON ca.id_cellphone_origin = ce.id_cellphone" +
            "       WHERE u.id_user = ?1", nativeQuery = true)
    List<GetCall> getCallsByUserId(Integer idClient);
}
