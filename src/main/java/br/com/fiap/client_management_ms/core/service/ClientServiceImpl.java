package br.com.fiap.client_management_ms.core.service;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.core.exception.ClientAlreadyExistsException;
import br.com.fiap.client_management_ms.core.exception.ClientNotFoundException;
import br.com.fiap.client_management_ms.core.mapper.ClientDtoDomainMapper;
import br.com.fiap.client_management_ms.core.port.in.ClientPortIn;
import br.com.fiap.client_management_ms.core.port.out.ClientPortOut;
import br.com.fiap.client_management_ms.core.updater.ClientUpdater;
import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.AllClientsResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.ClientResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.CreateClientResponseDto;

import java.util.Optional;

import static java.util.Objects.nonNull;

public class ClientServiceImpl implements ClientPortIn {

    private final AddressService addressService;
    private final ClientPortOut clientPortOut;
    private static final String EXISTS_WITH_CPF = "Client already exists with CPF: ";
    private static final String EXISTS_WITH_EMAIL = "Client already exists with e-mail: ";
    private static final String NOT_FOUND_WITH_ID = "Client not found with id: ";
    private static final String NOT_FOUND_WITH_EMAIL = "Client not found with e-mail: ";

    public ClientServiceImpl(AddressService addressService, ClientPortOut clientPortOut) {
        this.addressService = addressService;
        this.clientPortOut = clientPortOut;
    }

    @Override
    public CreateClientResponseDto createClient(ClientCreateRequestDto request) {
        Client client = ClientDtoDomainMapper.toClient(request);
        validateClientExistence(client);
        Address returnedAddress = getAddressData(client);
        client.setAddress(returnedAddress);
        return ClientDtoDomainMapper.toCreateClientResponseDto(clientPortOut.createClient(client));
    }

    @Override
    public ClientResponseDto getClientById(Long clientId) {
        Optional<Client> optionalClient = clientPortOut.getClientById(clientId);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(NOT_FOUND_WITH_ID + clientId);
        }
        return ClientDtoDomainMapper.toClientResponseDto(optionalClient.get());
    }

    @Override
    public ClientResponseDto getClientByEmail(String email) {
        Optional<Client> optionalClient = clientPortOut.getClientByEmail(email);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(NOT_FOUND_WITH_EMAIL + email);
        }
        return ClientDtoDomainMapper.toClientResponseDto(optionalClient.get());
    }

    @Override
    public AllClientsResponseDto getAllClients() {
        return ClientDtoDomainMapper.allClientsResponseDto(clientPortOut.getAllClients());
    }

    @Override
    public ClientResponseDto updateClient(Long clientId, ClientUpdateRequestDto request) {
        Client outdatedClient = findClientById(clientId);
        Client updatedClient = ClientDtoDomainMapper.updateRequestToClient(request);
        updateClientInformation(outdatedClient, updatedClient);
        return ClientDtoDomainMapper.toClientResponseDto(clientPortOut.updateClient(outdatedClient));
    }

    @Override
    public void deleteClientById(Long clientId) {
        if (!clientPortOut.existsClientById(clientId)) {
            throw new ClientNotFoundException(NOT_FOUND_WITH_ID + clientId);
        }
        clientPortOut.deleteClientById(clientId);
    }

    private void validateClientExistence(Client client) {
        if (clientPortOut.existsClientByCpf(client.getCpf())) {
            throw new ClientAlreadyExistsException(EXISTS_WITH_CPF + client.getCpf().getDocumentNumber());
        }
        if (clientPortOut.existsClientByEmail(client.getEmail())) {
            throw new ClientAlreadyExistsException(EXISTS_WITH_EMAIL + client.getEmail());
        }
    }

    private Address getAddressData(Client client) {
        return addressService.getAddressByApi(client.getAddress());
    }

    private Client findClientById(Long clientId) {
        return clientPortOut.getClientById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(NOT_FOUND_WITH_ID + clientId));
    }

    private void updateClientInformation(Client outdatedClient, Client updatedClient) {
        validateClientUniqueness(updatedClient);
        if (nonNull(updatedClient.getAddress()) && nonNull(updatedClient.getAddress().getCep())) {
            Address returnedAddress = getAddressData(updatedClient);
            updatedClient.setAddress(returnedAddress);
        }
        ClientUpdater.updateOutdatedClient(outdatedClient, updatedClient);
    }

    private void validateClientUniqueness(Client updatedClient) {
        if (nonNull(updatedClient.getCpf()) && clientPortOut.existsClientByCpf(updatedClient.getCpf())) {
            throw new ClientAlreadyExistsException(
                    EXISTS_WITH_CPF + updatedClient.getCpf().getDocumentNumber());
        }
        if (nonNull(updatedClient.getEmail()) && clientPortOut.existsClientByEmail(updatedClient.getEmail())) {
            throw new ClientAlreadyExistsException(
                    EXISTS_WITH_EMAIL + updatedClient.getEmail());
        }
    }
}
