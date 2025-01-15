package br.com.fiap.client_management_ms.framework.controller;

import br.com.fiap.client_management_ms.core.port.in.ClientPortIn;
import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.AllClientsResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.ClientResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.CreateClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientPortIn clientPortIn;

    public ClientController(ClientPortIn clientPortIn) {
        this.clientPortIn = clientPortIn;
    }

    @PostMapping
    public ResponseEntity<CreateClientResponseDto> createClient(@RequestBody @Valid ClientCreateRequestDto request) {
        CreateClientResponseDto createClientResponseDto = clientPortIn.createClient(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{clientId}")
                .buildAndExpand(createClientResponseDto.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(createClientResponseDto);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(clientPortIn.getClientById(clientId));
    }

    @GetMapping(params = "email")
    public ResponseEntity<ClientResponseDto> getClientByEmail(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(clientPortIn.getClientByEmail(email));
    }

    @GetMapping
    public ResponseEntity<AllClientsResponseDto> getAllClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientPortIn.getAllClients());
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ClientResponseDto> updateClient(
            @PathVariable Long clientId, @RequestBody @Valid ClientUpdateRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(clientPortIn.updateClient(clientId, request));
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long clientId) {
        clientPortIn.deleteClientById(clientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
