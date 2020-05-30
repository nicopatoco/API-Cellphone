package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "Select * from users where name = ?1", nativeQuery = true)
    List<User> findByName(String name);
    
    
    @Query(value = "select * from users where username = ?1 and password = ?2", nativeQuery = true)
    User userExists(String username, String password);
}
