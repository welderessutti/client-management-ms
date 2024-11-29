package br.com.fiap.client_management_ms.core.mapper;

import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.AllClientsResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.ClientResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.CreateClientResponseDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class ClientDtoDomainMapper {

    private ClientDtoDomainMapper() {
    }

    public static Client toClient(ClientCreateRequestDto request) {
        Client client = new Client();
        client.setFullName(request.getFullName());
        client.setEmail(request.getEmail());
        client.setMobilePhoneNumber(request.getMobilePhoneNumber());
        client.setCpf(CpfDtoDomainMapper.toCpf(request.getCpf()));
        client.setAddress(AddressDtoDomainMapper.toAddress(request.getAddress()));
        return client;
    }

    public static ClientResponseDto toClientResponseDto(Client client) {
        ClientResponseDto clientResponseDTO = new ClientResponseDto();
        clientResponseDTO.setId(client.getId());
        clientResponseDTO.setFullName(client.getFullName());
        clientResponseDTO.setEmail(client.getEmail());
        clientResponseDTO.setMobilePhoneNumber(client.getMobilePhoneNumber());
        clientResponseDTO.setCpf(CpfDtoDomainMapper.toCpfResponseDto(client.getCpf()));
        clientResponseDTO.setAddress(AddressDtoDomainMapper.toAddressResponseDto(client.getAddress()));
        return clientResponseDTO;
    }

    public static CreateClientResponseDto toCreateClientResponseDto(Client client) {
        CreateClientResponseDto createClientResponseDTO = new CreateClientResponseDto();
        createClientResponseDTO.setId(client.getId());
        return createClientResponseDTO;
    }

    public static AllClientsResponseDto allClientsResponseDto(List<Client> clientList) {
        AllClientsResponseDto allClientsResponseDTO = new AllClientsResponseDto();
        List<ClientResponseDto> clientResponseDtoList = new ArrayList<>();
        clientList.forEach(client -> {
            ClientResponseDto clientResponseDTO = toClientResponseDto(client);
            clientResponseDtoList.add(clientResponseDTO);
        });
        allClientsResponseDTO.setClients(clientResponseDtoList);
        return allClientsResponseDTO;
    }

    public static Client updateRequestToClient(ClientUpdateRequestDto request) {
        Client client = new Client();
        if (nonNull(request.getFullName()))
            client.setFullName(request.getFullName());
        if (nonNull(request.getEmail()))
            client.setEmail(request.getEmail());
        if (nonNull(request.getMobilePhoneNumber()))
            client.setMobilePhoneNumber(request.getMobilePhoneNumber());
        if (nonNull(request.getCpf()))
            client.setCpf(CpfDtoDomainMapper.updateRequestToCpf(request.getCpf()));
        if (nonNull(request.getAddress())) {
            client.setAddress(AddressDtoDomainMapper.updateRequestToAddress(request.getAddress()));
        }
        return client;
    }
}
