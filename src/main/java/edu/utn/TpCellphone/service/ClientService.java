package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Clients;
import edu.utn.TpCellphone.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ClientService {

    private final ClientRepository CLIENT_REPOSITORY;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.CLIENT_REPOSITORY = clientRepository;
    }

    public void addClient(Clients newClients) {
        CLIENT_REPOSITORY.save(newClients);
    }

    public Optional<Clients> getById(Integer id_client) {
        return CLIENT_REPOSITORY.findById(id_client);
    }

    public List<Clients> getAll(String name) {
        if(isNull(name)) {
            return CLIENT_REPOSITORY.findAll();
        }
        return CLIENT_REPOSITORY.findByName(name);
    }
}
