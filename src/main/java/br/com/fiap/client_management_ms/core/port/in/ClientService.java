package br.com.fiap.client_management_ms.core.port.in;

import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.AllClientsResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.ClientResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.CreateClientResponseDto;

public interface ClientService {

    CreateClientResponseDto createClient(ClientCreateRequestDto request);

    ClientResponseDto getClientById(Long clientId);

    ClientResponseDto getClientByEmail(String email);

    AllClientsResponseDto getAllClients();

    ClientResponseDto updateClient(Long clientId, ClientUpdateRequestDto request);

    void deleteClientById(Long clientId);
}
