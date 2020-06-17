package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.exceptions.BillNotFoundException;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.*;
import edu.utn.TpCellphone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public ResponseEntity<GetUserReduce> addUser(@RequestBody User newUsers) {
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

    @GetMapping("/projection/{idUser}")
    public ResponseEntity<GetUserReduce> getUserReduce(@PathVariable Integer idUser) {
        return USER_SERVICE.getReduceUser(idUser);
    }

    //TOP 10 CALLS OF X USER
    @GetMapping("/{idClient}/top10destinations")
    public ResponseEntity<List<GetUserTop10Destinations>> getTop10DestinationUserById(@PathVariable Integer idClient) throws CallNotFoundException {
        List<GetUserTop10Destinations> calls = USER_SERVICE.getTop10DestinationUserById(idClient);
        ResponseEntity<List<GetUserTop10Destinations>> responseEntity;

        if(!calls.isEmpty()){
            responseEntity = ResponseEntity.ok(calls);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CallNotFoundException();
        }
        return responseEntity;
    }
    
    //GET CALLS OF X USER BETWEEN RANGE OF DATES
    @GetMapping("/{idClient}/calls/dateFrom/{dateFrom}/dateTo/{dateTo}")
    public ResponseEntity<List<GetCall>> getCallsByRangeDate(@PathVariable Integer idClient, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) throws CallNotFoundException {
        List<GetCall> calls = USER_SERVICE.getCallsByRangeDate(idClient, dateFrom, dateTo);
        ResponseEntity<List<GetCall>> responseEntity;

        if(!calls.isEmpty()){
            responseEntity = ResponseEntity.ok(calls);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CallNotFoundException();
        }
        return responseEntity;
    }

    //GET BILLS OF X USER BETWEEN RANGE OF DATES
    @GetMapping("/{idClient}/bills/dateFrom/{dateFrom}/dateTo/{dateTo}")
    public ResponseEntity<List<GetBill>> getBillsByRangeDate(@PathVariable Integer idClient, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) throws BillNotFoundException {
        List<GetBill> calls = USER_SERVICE.getBillsByRangeDate(idClient, dateFrom, dateTo);
        ResponseEntity<List<GetBill>> responseEntity;

        if(!calls.isEmpty()){
            responseEntity = ResponseEntity.ok(calls);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new BillNotFoundException();
        }
        return responseEntity;
    }
}
