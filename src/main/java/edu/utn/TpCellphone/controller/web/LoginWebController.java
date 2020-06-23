package edu.utn.TpCellphone.controller.web;

import edu.utn.TpCellphone.controller.LoginController;
import edu.utn.TpCellphone.dto.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
/**
 * API filter for admins
 * http://localhost/admin
 */
@RequestMapping("/login")
public class LoginWebController {

    private LoginController loginController;

    @Autowired
    public LoginWebController(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * LOGIN
     */

    @PostMapping("/admin/")
    public ResponseEntity loginAdmin(@RequestBody LoginRequestDto loginRequestDto) throws NoSuchAlgorithmException {
        return this.loginController.login(loginRequestDto, "admin");
    }

    @PostMapping("/client/")
    public ResponseEntity loginClient(@RequestBody LoginRequestDto loginRequestDto) throws NoSuchAlgorithmException {
        return this.loginController.login(loginRequestDto, "client");
    }

    @PostMapping("/antenna/")
    public ResponseEntity loginAntenna(@RequestBody LoginRequestDto loginRequestDto) throws NoSuchAlgorithmException {
        return this.loginController.login(loginRequestDto, "antenna");
    }

    @PostMapping("/logout/")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        return this.loginController.logout(token);
    }

}
