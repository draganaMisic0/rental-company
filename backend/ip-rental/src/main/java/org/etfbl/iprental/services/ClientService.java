package org.etfbl.iprental.services;


import org.etfbl.iprental.models.ClientEntity;
import org.etfbl.iprental.models.DTO.ClientDTO;
import org.etfbl.iprental.models.requests.ClientRequest;
import org.etfbl.iprental.models.requests.ClientStatusChangeRequest;
import org.etfbl.iprental.models.requests.LoginRequest;
import org.etfbl.iprental.repositories.ClientRepository;
import org.etfbl.iprental.utils.mappers.ClientMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    public ClientDTO getClientById(Integer id) {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return clientMapper.toDto(client);
    }

    public ClientDTO getClientByUsername(String username) {
        ClientEntity client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return clientMapper.toDto(client);
    }

    public ClientDTO registerClient(ClientRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);

        if (request.getActive() == null) {
            request.setActive((byte) 1);
        }

        ClientEntity saved = clientRepository.save(clientMapper.toEntity(request));
        return clientMapper.toDto(saved);
    }

    public boolean verifyClientLogin(LoginRequest loginRequest) {
        ClientEntity client = clientRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return passwordEncoder.matches(loginRequest.getPassword(), client.getPassword());
    }

    public void deleteClientById(Integer id) {
        clientRepository.deleteById(id);
    }

    public void deleteClientByUsername(String username) {
        ClientEntity client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        clientRepository.delete(client);
    }

    public void deleteAllClients() {
        clientRepository.deleteAll();
    }

    public ClientDTO changeClientStatus(Integer id, ClientStatusChangeRequest request) {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Byte valueToSet = request.getNewStatus() ? (byte)1 : (byte)0;

        client.setActive(valueToSet);

        return clientMapper.toDto(clientRepository.save(client));
    }
}

