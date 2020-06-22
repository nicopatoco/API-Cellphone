package edu.utn.TpCellphone.controller.web;

import edu.utn.TpCellphone.controller.*;
import edu.utn.TpCellphone.dto.LoginRequestDto;
import edu.utn.TpCellphone.exceptions.BillNotFoundException;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.exceptions.PriceNotFoundException;
import edu.utn.TpCellphone.model.Bill;
import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.model.Price;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.GetCall;
import edu.utn.TpCellphone.projections.GetUserReduce;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
/**
 * API filter for admins
 * http://localhost/admin
 */
@RequestMapping("/admin")
public class AdminController {

    private LoginController loginController;
    private UserController userController;
    private CellphoneController cellphoneController;
    private PriceController priceController;
    private BillController billController;

    public AdminController(LoginController loginController, UserController userController, CellphoneController cellphoneController, PriceController priceController, BillController billController) {
        this.loginController = loginController;
        this.userController = userController;
        this.cellphoneController = cellphoneController;
        this.priceController = priceController;
        this.billController = billController;
    }

    /**
     * LOGIN
     */

    @PostMapping("/login/")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) throws NoSuchAlgorithmException {
        return this.loginController.login(loginRequestDto, "admin");
    }

    @PostMapping("/logout/")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        return this.loginController.logout(token);
    }

    /**
     * USERS
     */

    @PostMapping("/user/")
    public ResponseEntity<GetUserReduce> addUser(@RequestBody User newUsers) {
        return this.userController.addUser(newUsers);
    }

    @GetMapping("/user/")
    public List<User> getAll(@RequestParam(required = false) String name) {
        return this.userController.getAll(name);
    }

    @GetMapping("/user/{idUser}")
    public Optional<User> getUserById(@PathVariable Integer idUser) {
        return this.userController.getUserById(idUser);
    }

    @DeleteMapping("/user/")
    public void deleteUser(@RequestBody User client) {
        this.userController.deleteUser(client);
    }

    @PutMapping("/user/{idUser}")
    public User update(@RequestBody User user, @PathVariable int idUser) throws NoSuchAlgorithmException {
        return this.userController.update(user, idUser);
    }

    @GetMapping("/user/{idUser}/calls")
    public ResponseEntity<List<GetCall>> getCallsByUserId(@PathVariable Integer idUser) throws CallNotFoundException {
        return this.userController.getCallsByUserId(idUser);
    }


    /**
     * CELLPHONE
     */

    @PostMapping("/cellphone/")
    public void addCellphone(@RequestBody Cellphone newCellphone) {
        this.cellphoneController.addCellphone(newCellphone);
    }

    @GetMapping("/cellphone/")
    public List<Cellphone> getAll() {
        return this.cellphoneController.getAll();
    }

    @GetMapping("/cellphone/{idCellphone}")
    public Optional<Cellphone> getCellphoneById(@PathVariable Integer idCellphone) {
        return this.cellphoneController.getCellphoneById(idCellphone);
    }

    @DeleteMapping("/cellphone/")
    public void deleteCellphone(@RequestBody Cellphone cellphone) {
        this.cellphoneController.deleteCellphone(cellphone);
    }

    /**
     * PRICES
     */

    @GetMapping("/price/")
    public ResponseEntity<List<Price>> getAllPrices() throws PriceNotFoundException {
        return this.priceController.getAllPrices();
    }

    @GetMapping("/price/{idPrice}")
    public ResponseEntity<Price> getPriceById(@PathVariable Integer idPrice) throws PriceNotFoundException {
        return this.priceController.getPriceById(idPrice);
    }

    /**
     * BILL
     */

    @GetMapping("/bill/")
    public ResponseEntity<List<Bill>> getAllBills() throws BillNotFoundException {
        return this.billController.getAllBills();
    }

    @GetMapping("/bill/{idBill}")
    public ResponseEntity<Bill> getByIdBill(@PathVariable Integer idBill) throws BillNotFoundException {
        return this.billController.getByIdBill(idBill);
    }
}
