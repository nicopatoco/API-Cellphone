package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Users;
import edu.utn.TpCellphone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final UserRepository USER_REPOSITORY;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.USER_REPOSITORY = userRepository;
    }

    public Users addUser(Users newUsers) {
        return USER_REPOSITORY.save(newUsers);
    }

    public Optional<Users> getById(Integer id_client) {
        return USER_REPOSITORY.findById(id_client);
    }

    public List<Users> getAll(String name) {
        if(isNull(name)) {
            return USER_REPOSITORY.findAll();
        }
        return USER_REPOSITORY.findByName(name);
    }

    public void delete(Users client) {
        USER_REPOSITORY.delete(client);
    }
    
    public Users update(Users user, int id_user) {
        Users userToUpdate = USER_REPOSITORY.getOne(id_user);
        userToUpdate.setId_city(user.getId_city());
        userToUpdate.setId(user.getId());
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setUser_type(user.getUser_type());
        return USER_REPOSITORY.save(userToUpdate);
    }
}
