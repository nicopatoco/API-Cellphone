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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller

public class UserController {

    private final UserService USER_SERVICE;

    @Autowired
    public UserController(UserService clientService) {
        this.USER_SERVICE = clientService;
    }

    public Optional<User> getUserById(Integer id_client) {
        return USER_SERVICE.getById(id_client);
    }

    public ResponseEntity<GetUserReduce> addUser(User newUsers) {
        return USER_SERVICE.addUser(newUsers);
    }

    public List<User> getAll(String name) {
        return USER_SERVICE.getAll(name);
    }

    public void deleteUser(User client) {
        USER_SERVICE.delete(client);
    }

    public User update(User user, int id_user) throws NoSuchAlgorithmException {
        return USER_SERVICE.update(user, id_user);
    }

    public User login(String username, String password, String type) throws NoSuchAlgorithmException {
        return USER_SERVICE.login(username, password, type);
    }

    public ResponseEntity<GetUserReduce> getUserReduce(Integer idUser) {
        return USER_SERVICE.getReduceUser(idUser);
    }

    public ResponseEntity<List<GetUserTop10Destinations>> getTop10DestinationUserById(Integer idClient) throws CallNotFoundException {
        List<GetUserTop10Destinations> calls = USER_SERVICE.getTop10DestinationUserById(idClient);
        ResponseEntity<List<GetUserTop10Destinations>> responseEntity;

        if (!calls.isEmpty()) {

            responseEntity = ResponseEntity.ok(calls);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CallNotFoundException();
        }
        return responseEntity;
    }

    public ResponseEntity<List<GetCall>> getCallsByRangeDate(Integer idClient, Date dateFrom, Date dateTo) throws CallNotFoundException {
        List<GetCall> calls = USER_SERVICE.getCallsByRangeDate(idClient, dateFrom, dateTo);
        ResponseEntity<List<GetCall>> responseEntity;

        if (!calls.isEmpty()) {

            responseEntity = ResponseEntity.ok(calls);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CallNotFoundException();
        }
        return responseEntity;
    }

    public ResponseEntity<List<GetBill>> getBillsByRangeDate(Integer idClient, Date dateFrom, Date dateTo) throws BillNotFoundException {
        List<GetBill> calls = USER_SERVICE.getBillsByRangeDate(idClient, dateFrom, dateTo);
        ResponseEntity<List<GetBill>> responseEntity;

        if (!calls.isEmpty()) {

            responseEntity = ResponseEntity.ok(calls);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new BillNotFoundException();
        }
        return responseEntity;
    }
}
