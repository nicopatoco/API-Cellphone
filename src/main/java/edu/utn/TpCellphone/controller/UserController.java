package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Users;
import edu.utn.TpCellphone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService USER_SERVICE;

    @Autowired
    public UserController(UserService clientService) {
        this.USER_SERVICE = clientService;
    }

    @GetMapping("/{id_client}")
    public Optional<Users> getUserById(@PathVariable Integer id_client) {
        return USER_SERVICE.getById(id_client);
    }

    @PostMapping("/")
    public Users addUser(@RequestBody Users newUsers) {
        return USER_SERVICE.addUser(newUsers);
    }

    @GetMapping("/")
    public List<Users> getAll(@RequestParam(required = false) String name) {
        return USER_SERVICE.getAll(name);
    }

    @DeleteMapping("/")
    public void deleteUser(@RequestBody Users client) {
        USER_SERVICE.delete(client);
    }
    
    @PutMapping("/{id_user}")
    public Users update(@RequestBody Users user, @PathVariable int id_user) {
        return USER_SERVICE.update(user, id_user);
    }

}
