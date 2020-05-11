package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.model.Clients;
import edu.utn.TpCellphone.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientServiceTest {
    private ClientService clientService;
    private ClientRepository repository;
    private Clients clients;

    @BeforeEach
    public void setUp() {
        this.repository = mock(ClientRepository.class);
        this.clientService = new ClientService(repository);
        this.clients = new Clients(5, "35885684", "juan", "perez", new Cities(1, 1, "miramar", 221));
    }

    @Test
    public void addClientTest() {
        when(repository.save(clients)).thenReturn(clients);
        clientService.addClient(clients);

        verify(repository, times(1)).save(clients);
    }

    @Test
    public void getByIdTest() {
        when(repository.findById(5)).thenReturn(java.util.Optional.ofNullable(clients));
        Optional<Clients> returnedClient = clientService.getById(5);

        assertEquals(clients, returnedClient.get());
    }

    @Test
    public void getAllTest() {
        List<Clients> clientsList = new ArrayList<>();
        clientsList.add(clients);
        when(repository.findAll()).thenReturn(clientsList);
        List<Clients> returnedClient = clientService.getAll(null);

        assertEquals(clientsList, returnedClient);
    }

    @Test
    public void getAllTest2() {
        List<Clients> clientsList = new ArrayList<>();
        clientsList.add(clients);
        when(repository.findByName("juan")).thenReturn(clientsList);
        List<Clients> returnedClient = clientService.getAll("juan");

        assertEquals(clientsList, returnedClient);
    }

    @Test
    public void deleteTest() {
        doNothing().when(repository).delete(clients);
        clientService.delete(clients);

        verify(repository, times(1)).delete(clients);
    }

/*
    public void delete(Clients client) {
        CLIENT_REPOSITORY.delete(client);
    }

     */

}
