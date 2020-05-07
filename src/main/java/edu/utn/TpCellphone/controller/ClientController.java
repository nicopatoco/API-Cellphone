package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Clients;
import edu.utn.TpCellphone.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService CLIENT_SERVICE;

    @Autowired
    public ClientController(ClientService clientService) {
        this.CLIENT_SERVICE = clientService;
    }

    @GetMapping("/{id_client}")
    public Optional<Clients> getClientById(@PathVariable Integer id_client) {
        return CLIENT_SERVICE.getById(id_client);
    }

    @PostMapping("/")
    public void addClient(@RequestBody Clients newClients) {
        CLIENT_SERVICE.addClient(newClients);
    }

    @GetMapping("/")
    public List<Clients> getAll(@RequestParam(required = false) String name) {
        return CLIENT_SERVICE.getAll(name);
    }

    @DeleteMapping("/")
    public void deleteClient(@RequestBody Clients client) {
        CLIENT_SERVICE.delete(client);
    }
}
