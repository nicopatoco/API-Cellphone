package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.model.Cities;
import edu.utn.TpCellphone.model.Clients;
import edu.utn.TpCellphone.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientControllerTest {
    private ClientController clientController;
    private ClientService service;
    private Clients clients;

    private ClientService service2;
    private ClientController clientController2;

    @BeforeEach
    public void setUp() {
        this.service = mock(ClientService.class);
        this.clientController = new ClientController(service);
        this.clients = new Clients(5, "35885684", "juan", "perez", new Cities(1, 1, "miramar", 221));
    }

    @Test
    public void testGetClientById() {
        when(service.getById(5)).thenReturn(java.util.Optional.of(clients));
        Optional<Clients> returnedClient = clientController.getClientById(5);

        assertEquals(clients, returnedClient.get());
    }

    @Test
    public void testAddClient() {
        doNothing().when(service).addClient(clients);
        clientController.addClient(clients);

        verify(service, times(1)).addClient(clients);
    }

    @Test
    public void testGetAllClient() {
        List<Clients> clientsList = new ArrayList<>();
        clientsList.add(clients);
        when(service.getAll("juan")).thenReturn(clientsList);
        List<Clients> returnedList = clientController.getAll("juan");

        assertEquals(clientsList, returnedList);
    }

    @Test
    public void testDeleteClient() {
        doNothing().when(service).delete(clients);
        clientController.deleteClient(clients);

        verify(service, times(1)).delete(clients);
    }

}