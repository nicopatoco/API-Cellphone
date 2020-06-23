package edu.utn.TpCellphone.controller.web;


import edu.utn.TpCellphone.controller.*;
import edu.utn.TpCellphone.dto.LoginRequestDto;
import edu.utn.TpCellphone.exceptions.BillNotFoundException;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.model.User;
import edu.utn.TpCellphone.projections.GetBill;
import edu.utn.TpCellphone.projections.GetCall;
import edu.utn.TpCellphone.projections.GetUserTop10Destinations;
import edu.utn.TpCellphone.session.SessionManager;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@RestController
/**
 * API filter for clients
 * http://localhost/client
 */
@RequestMapping("/client")
public class ClientController {

    private LoginController loginController;
    private UserController userController;
    private CellphoneController cellphoneController;
    private PriceController priceController;
    private BillController billController;

    private final SessionManager sessionManager;


    public ClientController(LoginController loginController, UserController userController, CellphoneController cellphoneController, PriceController priceController, BillController billController, SessionManager sessionManager) {
        this.loginController = loginController;
        this.userController = userController;
        this.cellphoneController = cellphoneController;
        this.priceController = priceController;
        this.billController = billController;
        this.sessionManager = sessionManager;
    }


    /**
     * LOGIN
     */

    @PostMapping("/logout/")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        return this.loginController.logout(token);
    }

    /**
     * USER
     */

    @GetMapping("/user/top10destinations")
    public ResponseEntity<List<GetUserTop10Destinations>> getTop10DestinationUserById(@RequestHeader("Authorization") String sessionToken) throws CallNotFoundException {
        User user = sessionManager.getCurrentUser(sessionToken);
        return this.userController.getTop10DestinationUserById(user.getIdUser());
    }


    @GetMapping("/user/calls/dateFrom/{dateFrom}/dateTo/{dateTo}")
    public ResponseEntity<List<GetCall>> getCallsByRangeDate(@RequestHeader("Authorization") String sessionToken, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) throws CallNotFoundException {
        User user = sessionManager.getCurrentUser(sessionToken);
        return this.userController.getCallsByRangeDate(user.getIdUser(), dateFrom, dateTo);
    }

    @GetMapping("/user/bills/dateFrom/{dateFrom}/dateTo/{dateTo}")
    public ResponseEntity<List<GetBill>> getBillsByRangeDate(@RequestHeader("Authorization") String sessionToken, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) throws BillNotFoundException {
        User user = sessionManager.getCurrentUser(sessionToken);
        return this.userController.getBillsByRangeDate(user.getIdUser(), dateFrom, dateTo);
    }

}

