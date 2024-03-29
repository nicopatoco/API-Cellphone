package edu.utn.TpCellphone.controller.web;

import edu.utn.TpCellphone.controller.CallController;
import edu.utn.TpCellphone.controller.LoginController;
import edu.utn.TpCellphone.dto.CallAddDto;
import edu.utn.TpCellphone.dto.LoginRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
/**
 * API filter for admins
 * http://localhost/antenna
 */
@RequestMapping("/antenna")
public class AntennaController {

    private LoginController loginController;
    private CallController callController;

    public AntennaController(LoginController loginController, CallController callController) {
        this.loginController = loginController;
        this.callController = callController;
    }
    
    /**
     * Call
     * @param call
     * @return
     * @throws SQLException
     */

    @PostMapping("/call/")
    public ResponseEntity addCall(@RequestBody CallAddDto call) throws SQLException {
        return this.callController.addCall(call);
    }

}
