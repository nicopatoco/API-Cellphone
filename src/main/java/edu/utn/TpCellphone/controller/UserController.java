package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService USER_SERVICE;

    @Autowired
    public UserController(UserService clientService) {
        this.USER_SERVICE = clientService;
    }

    @GetMapping("/{id_client}")
    public Optional<User> getUserById(@PathVariable Integer id_client) {
        return USER_SERVICE.getById(id_client);
    }

    @PostMapping("/")
    public User addUser(@RequestBody User newUsers) {
        return USER_SERVICE.addUser(newUsers);
    }

    @GetMapping("/")
    public List<User> getAll(@RequestParam(required = false) String name) {
        return USER_SERVICE.getAll(name);
    }

    @DeleteMapping("/")
    public void deleteUser(@RequestBody User client) {
        USER_SERVICE.delete(client);
    }
    
    @PutMapping("/{id_user}")
    public User update(@RequestBody User user, @PathVariable int id_user) {
        return USER_SERVICE.update(user, id_user);
    }
    
    @GetMapping("/login/")
    public User login(@RequestParam String username, @RequestParam String password) {
        return USER_SERVICE.login(username, password);
    }

}
