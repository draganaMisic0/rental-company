package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.DTO.ClientDTO;
import org.etfbl.iprental.models.requests.ClientRequest;
import org.etfbl.iprental.models.requests.ClientStatusChangeRequest;
import org.etfbl.iprental.models.requests.LoginRequest;
import org.etfbl.iprental.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ClientDTO> getClientByUsername(@PathVariable String username) {
        return ResponseEntity.ok(clientService.getClientByUsername(username));
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDTO> registerClient(@RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(clientService.registerClient(clientRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> loginClient(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(clientService.verifyClientLogin(loginRequest));
    }

    @PutMapping("/change_status/{id}")
    public ResponseEntity<ClientDTO> changeClientStatus(@PathVariable Integer id, @RequestBody ClientStatusChangeRequest request) {
        return ResponseEntity.ok(clientService.changeClientStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Integer id) {
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<Void> deleteClientByUsername(@PathVariable String username) {
            clientService.deleteClientByUsername(username);
            return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllClients() {
        clientService.deleteAllClients();
        return ResponseEntity.noContent().build();
    }
}
