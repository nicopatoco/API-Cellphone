package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.City;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.repository.CityRepository;
import edu.utn.TpCellphone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User addUser(User newUser) {
        Optional<City> city = CITY_REPOSITORY.findById(newUser.getCity().getIdCity());
        newUser.setCity(city.get());
        return USER_REPOSITORY.save(newUser);
    }

    public Optional<User> getById(Integer id_user) {
        return USER_REPOSITORY.findById(id_user);
    }

    public List<User> getAll(String name) {
        if(isNull(name)) {
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
}
