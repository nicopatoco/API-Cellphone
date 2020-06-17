package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.*;
import edu.utn.TpCellphone.repository.CityRepository;
import edu.utn.TpCellphone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final UserRepository USER_REPOSITORY;
    private final CityRepository CITY_REPOSITORY;

    @Autowired
    public UserService(UserRepository userRepository, CityRepository city_repository) {
        this.USER_REPOSITORY = userRepository;
        this.CITY_REPOSITORY = city_repository;
    }

    public ResponseEntity<GetUserReduce> addUser(User newUser) {
        Optional<City> city = CITY_REPOSITORY.findById(newUser.getCity().getIdCity());
        newUser.setCity(city.get());
        User userResponse = USER_REPOSITORY.save(newUser);
        return this.getReduceUser(userResponse.getIdUser());
    }

    public Optional<User> getById(Integer id_user) {
        return USER_REPOSITORY.findById(id_user);
    }

    public List<User> getAll(String name) {
        if (isNull(name)) {
            return USER_REPOSITORY.findAll();
        }
        return USER_REPOSITORY.findByName(name);
    }

    public void delete(User users) {
        USER_REPOSITORY.delete(users);
    }

    public User update(User user, int id_user) {
        User userToUpdate = USER_REPOSITORY.getOne(id_user);
        userToUpdate.getCity().setIdCity(user.getCity().getIdCity());
        userToUpdate.setId(user.getId());
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setUserType(user.getUserType());
        return USER_REPOSITORY.save(userToUpdate);
    }

    public User login(String username, String password) {
        User user = USER_REPOSITORY.userExists(username, password);
        return Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("User does not exists"));
    }

    public ResponseEntity<GetUserReduce> getReduceUser(int idUser) {
        ResponseEntity<GetUserReduce> response;
        GetUserReduce user = USER_REPOSITORY.getUserById(idUser);
        if (user != null) {
            response = ResponseEntity.ok(user);
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }


    public List<GetUserTop10Destinations> getTop10DestinationUserById(Integer idClient) {
        return USER_REPOSITORY.getTop10DestinationUserById(idClient);
    }

    public List<GetCall> getCallsByRangeDate(Integer idClient, Date dateFrom, Date dateTo) {
        return USER_REPOSITORY.getCallsByRangeDate(idClient, dateFrom, dateTo);
    }

    public List<GetBill> getBillsByRangeDate(Integer idClient, Date dateFrom, Date dateTo) {
        return USER_REPOSITORY.getBillsByRangeDate(idClient, dateFrom, dateTo);
    }

}
