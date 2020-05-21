package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(value = "Select * from users where name = ?1", nativeQuery = true)
    List<Users> findByName(String name);
}
