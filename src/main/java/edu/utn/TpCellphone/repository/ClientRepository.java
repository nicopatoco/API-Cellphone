package edu.utn.TpCellphone.repository;

import edu.utn.TpCellphone.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Clients, Integer> {

    @Query(value = "Select * from clients where name = ?1", nativeQuery = true)
    List<Clients> findByName(String name);
}
