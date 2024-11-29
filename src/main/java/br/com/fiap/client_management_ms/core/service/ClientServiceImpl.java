package br.com.fiap.client_management_ms.core.service;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.core.exception.ClientAlreadyExistsException;
import br.com.fiap.client_management_ms.core.exception.ClientNotFoundException;
import br.com.fiap.client_management_ms.core.mapper.ClientDtoDomainMapper;
import br.com.fiap.client_management_ms.core.port.in.ClientService;
import br.com.fiap.client_management_ms.core.port.out.ClientAdapter;
import br.com.fiap.client_management_ms.core.updater.ClientUpdater;
import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.AllClientsResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.ClientResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.CreateClientResponseDto;

import java.util.Optional;

import static java.util.Objects.nonNull;

public class ClientServiceImpl implements ClientService {

    private final AddressService addressService;
    private final ClientAdapter clientAdapter;
    private static final String EXISTS_WITH_CPF = "Client already exists with CPF: ";
    private static final String EXISTS_WITH_EMAIL = "Client already exists with e-mail: ";
    private static final String NOT_FOUND_WITH_ID = "Client not found with id: ";
    private static final String NOT_FOUND_WITH_EMAIL = "Client not found with e-mail: ";

    public ClientServiceImpl(AddressService addressService, ClientAdapter clientAdapter) {
        this.addressService = addressService;
        this.clientAdapter = clientAdapter;
    }

    @Override
    public CreateClientResponseDto createClient(ClientCreateRequestDto request) {
        Client client = ClientDtoDomainMapper.toClient(request);
        if (clientAdapter.existsClientByCpf(client.getCpf())) {
            throw new ClientAlreadyExistsException(
                    EXISTS_WITH_CPF + client.getCpf().getDocumentNumber());
        }
        if (clientAdapter.existsClientByEmail(client.getEmail())) {
            throw new ClientAlreadyExistsException(
                    EXISTS_WITH_EMAIL + client.getEmail());
        }
        Address returnedAddress = addressService.getAddressByApi(client.getAddress());
        client.setAddress(returnedAddress);
        return ClientDtoDomainMapper.toCreateClientResponseDto(clientAdapter.createClient(client));
    }

    @Override
    public ClientResponseDto getClientById(Long clientId) {
        Optional<Client> optionalClient = clientAdapter.getClientById(clientId);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(NOT_FOUND_WITH_ID + clientId);
        }
        return ClientDtoDomainMapper.toClientResponseDto(optionalClient.get());
    }

    @Override
    public ClientResponseDto getClientByEmail(String email) {
        Optional<Client> optionalClient = clientAdapter.getClientByEmail(email);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(NOT_FOUND_WITH_EMAIL + email);
        }
        return ClientDtoDomainMapper.toClientResponseDto(optionalClient.get());
    }

    @Override
    public AllClientsResponseDto getAllClients() {
        return ClientDtoDomainMapper.allClientsResponseDto(clientAdapter.getAllClients());
    }

    @Override
    public ClientResponseDto updateClient(Long clientId, ClientUpdateRequestDto request) {
        Optional<Client> optionalClient = clientAdapter.getClientById(clientId);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(NOT_FOUND_WITH_ID + clientId);
        }

        Client outdatedClient = optionalClient.get();
        Client updatedClient = ClientDtoDomainMapper.updateRequestToClient(request);

        if (nonNull(updatedClient.getCpf()) && clientAdapter.existsClientByCpf(updatedClient.getCpf())) {
            throw new ClientAlreadyExistsException(
                    EXISTS_WITH_CPF + updatedClient.getCpf().getDocumentNumber());
        }
        if (nonNull(updatedClient.getEmail()) && clientAdapter.existsClientByEmail(updatedClient.getEmail())) {
            throw new ClientAlreadyExistsException(
                    EXISTS_WITH_EMAIL + updatedClient.getEmail());
        }
        if (nonNull(updatedClient.getAddress()) && nonNull(updatedClient.getAddress().getCep())) {
            Address returnedAddress = addressService.getAddressByApi(updatedClient.getAddress());
            updatedClient.setAddress(returnedAddress);
        }
        ClientUpdater.updateOutdatedClient(outdatedClient, updatedClient);
        return ClientDtoDomainMapper.toClientResponseDto(clientAdapter.updateClient(outdatedClient));
    }

    @Override
    public void deleteClientById(Long clientId) {
        if (!clientAdapter.existsClientById(clientId)) {
            throw new ClientNotFoundException(NOT_FOUND_WITH_ID + clientId);
        }
        clientAdapter.deleteClientById(clientId);
    }
}
