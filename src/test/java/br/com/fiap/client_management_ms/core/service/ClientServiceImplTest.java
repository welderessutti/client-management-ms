package br.com.fiap.client_management_ms.core.service;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.core.domain.Cpf;
import br.com.fiap.client_management_ms.core.exception.ClientAlreadyExistsException;
import br.com.fiap.client_management_ms.core.exception.ClientNotFoundException;
import br.com.fiap.client_management_ms.core.exception.InvalidCpfDocumentNumberException;
import br.com.fiap.client_management_ms.core.port.in.ClientService;
import br.com.fiap.client_management_ms.core.port.out.ClientAdapter;
import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.AllClientsResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.ClientResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.CreateClientResponseDto;
import br.com.fiap.client_management_ms.utils.ClientHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private ClientService clientService;

    @Mock
    private ClientAdapter clientAdapter;

    @Mock
    private AddressService addressService;

    @Mock
    private Cpf cpf;

    private AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(addressService, clientAdapter);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CreateClient {

        @Test
        void shouldCreateClient() {
            // Arrange
            Long id = 1L;
            Client client = ClientHelper.createClientObject();
            client.setId(id);
            Address address = client.getAddress();
            address.setId(id);
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();

            when(clientAdapter.existsClientByCpf(any(Cpf.class))).thenReturn(false);
            when(clientAdapter.existsClientByEmail(any(String.class))).thenReturn(false);
            when(addressService.getAddressByApi(any(Address.class))).thenReturn(address);
            when(clientAdapter.createClient(any(Client.class))).thenReturn(client);

            // Act
            CreateClientResponseDto createdClientResponseDto = clientService.createClient(clientCreateRequestDto);

            // Assert
            assertThat(createdClientResponseDto).isNotNull().isInstanceOf(CreateClientResponseDto.class);
            assertThat(createdClientResponseDto.getId()).isNotNull();
            verify(clientAdapter, times(1)).existsClientByCpf(any(Cpf.class));
            verify(clientAdapter, times(1)).existsClientByEmail(any(String.class));
            verify(addressService, times(1)).getAddressByApi(any(Address.class));
            verify(clientAdapter, times(1)).createClient(any(Client.class));
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingCpf() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            Client client = ClientHelper.createClientObject();

            when(clientAdapter.existsClientByCpf(any(Cpf.class))).thenReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> clientService.createClient(clientCreateRequestDto))
                    .isInstanceOf(ClientAlreadyExistsException.class)
                    .hasMessage("Client already exists with CPF: " + client.getCpf().getDocumentNumber());
            verify(clientAdapter, times(1)).existsClientByCpf(any(Cpf.class));
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingEmail() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            Client client = ClientHelper.createClientObject();

            when(clientAdapter.existsClientByEmail(any(String.class))).thenReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> clientService.createClient(clientCreateRequestDto))
                    .isInstanceOf(ClientAlreadyExistsException.class)
                    .hasMessage("Client already exists with e-mail: " + client.getEmail());
            verify(clientAdapter, times(1)).existsClientByEmail(any(String.class));
        }

        @Test
        void shouldThrowInvalidCpfDocumentNumberExceptionWhenCreateClientWithInvalidCpf() {
            // Arrange
            String invalidCpf = "999.999.999-99";
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.getCpf().setDocumentNumber(invalidCpf);

            // Act & Assert
            assertThatThrownBy(() -> clientService.createClient(clientCreateRequestDto))
                    .isInstanceOf(InvalidCpfDocumentNumberException.class)
                    .hasMessage("Invalid CPF document number: 99999999999");
        }
    }

    @Nested
    class GetClient {

        @Test
        void shouldGetClientById() {
            // Arrange
            Long id = 1L;
            Client client = ClientHelper.createClientObject();
            client.setId(id);

            when(clientAdapter.getClientById(any(Long.class))).thenReturn(Optional.of(client));

            // Act
            ClientResponseDto foundClientResponseDto = clientService.getClientById(id);

            // Assert
            assertThat(foundClientResponseDto).isNotNull().isInstanceOf(ClientResponseDto.class);
            assertThat(foundClientResponseDto.getId()).isNotNull().isEqualTo(id);
            assertThat(foundClientResponseDto.getFullName()).isNotNull();
            assertThat(foundClientResponseDto.getEmail()).isNotNull();
            assertThat(foundClientResponseDto.getMobilePhoneNumber()).isNotNull();
            assertThat(foundClientResponseDto.getCpf()).isNotNull();
            assertThat(foundClientResponseDto.getAddress()).isNotNull();
            verify(clientAdapter, times(1)).getClientById(any(Long.class));
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenGetClientByIdIsNotFound() {
            // Arrange
            Long clientId = 1L;

            when(clientAdapter.getClientById(any(Long.class))).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() ->
                    clientService.getClientById(clientId))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessage("Client not found with id: " + clientId);
            verify(clientAdapter, times(1)).getClientById(any(Long.class));
        }

        @Test
        void shouldGetClientByEmail() {
            // Arrange
            Long id = 1L;
            Client client = ClientHelper.createClientObject();
            client.setId(id);
            String email = "marcos@silva.com";

            when(clientAdapter.getClientByEmail(any(String.class))).thenReturn(Optional.of(client));

            // Act
            ClientResponseDto foundClientResponseDto = clientService.getClientByEmail(email);

            // Assert
            assertThat(foundClientResponseDto).isNotNull().isInstanceOf(ClientResponseDto.class);
            assertThat(foundClientResponseDto.getId()).isNotNull();
            assertThat(foundClientResponseDto.getFullName()).isNotNull();
            assertThat(foundClientResponseDto.getEmail()).isNotNull().isEqualTo(email);
            assertThat(foundClientResponseDto.getMobilePhoneNumber()).isNotNull();
            assertThat(foundClientResponseDto.getCpf()).isNotNull();
            assertThat(foundClientResponseDto.getAddress()).isNotNull();
            verify(clientAdapter, times(1)).getClientByEmail(any(String.class));
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenGetClientByEmailIsNotFound() {
            // Arrange
            String email = "not@exists.com";

            when(clientAdapter.getClientById(any(Long.class))).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() ->
                    clientService.getClientByEmail(email))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessage("Client not found with e-mail: " + email);
            verify(clientAdapter, times(1)).getClientByEmail(any(String.class));
        }

        @Test
        void shouldGetAllClients() {
            // Arrange
            Client client1 = ClientHelper.createClientObject();
            Client client2 = ClientHelper.createClientObject();
            List<Client> clientList = Arrays.asList(client1, client2);

            when(clientAdapter.getAllClients()).thenReturn(clientList);

            // Act
            AllClientsResponseDto allClientsResponseDto = clientService.getAllClients();

            // Assert
            assertThat(allClientsResponseDto).isNotNull().isInstanceOf(AllClientsResponseDto.class);
            assertThat(allClientsResponseDto.getClients())
                    .isNotNull()
                    .isExactlyInstanceOf(ArrayList.class)
                    .allSatisfy(client -> {
                        assertThat(client)
                                .isNotNull()
                                .isInstanceOf(ClientResponseDto.class);
                    });
            verify(clientAdapter, times(1)).getAllClients();
        }
    }

    @Nested
    class UpdateClient {

        @Test
        void shouldUpdateClient() {
            // Arrange
            Long id = 1L;
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
            Address updatedAddress = ClientHelper.createUpdatedAddressObject();
            Client client = ClientHelper.createClientObject();
            client.setId(id);
            client.getAddress().setId(id);
            Client updatedClient = ClientHelper.createUpdatedClientObject();
            updatedClient.setId(id);
            updatedClient.getAddress().setId(id);

            when(clientAdapter.getClientById(any(Long.class))).thenReturn(Optional.of(client));
            when(clientAdapter.existsClientByCpf(any(Cpf.class))).thenReturn(false);
            when(clientAdapter.existsClientByEmail(any(String.class))).thenReturn(false);
            when(addressService.getAddressByApi(any(Address.class))).thenReturn(updatedAddress);
            when(clientAdapter.updateClient(any(Client.class))).thenReturn(updatedClient);

            // Act
            ClientResponseDto updatedClientResponseDto = clientService.updateClient(id, clientUpdateRequestDto);

            // Assert
            assertThat(updatedClientResponseDto).isNotNull().isInstanceOf(ClientResponseDto.class);
            assertThat(updatedClientResponseDto.getId()).isNotNull().isEqualTo(id);
            assertThat(updatedClientResponseDto.getFullName()).isNotNull().isEqualTo(clientUpdateRequestDto.getFullName());
            assertThat(updatedClientResponseDto.getEmail()).isNotNull().isEqualTo(clientUpdateRequestDto.getEmail());
            assertThat(updatedClientResponseDto.getMobilePhoneNumber()).isNotNull().isEqualTo(clientUpdateRequestDto.getMobilePhoneNumber());
            assertThat(updatedClientResponseDto.getCpf().getDocumentNumber()).isNotNull().isEqualTo(clientUpdateRequestDto.getCpf().getDocumentNumber());
            assertThat(updatedClientResponseDto.getAddress().getId()).isNotNull().isEqualTo(id);
            verify(clientAdapter, times(1)).getClientById(any(Long.class));
            verify(clientAdapter, times(1)).existsClientByCpf(any(Cpf.class));
            verify(clientAdapter, times(1)).existsClientByEmail(any(String.class));
            verify(addressService, times(1)).getAddressByApi(any(Address.class));
            verify(clientAdapter, times(1)).updateClient(any(Client.class));
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenUpdateClientWithNotFoundId() {
            // Arrange
            Long id = 999L;
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();

            when(clientAdapter.getClientById(any(Long.class))).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() ->
                    clientService.updateClient(id, clientUpdateRequestDto))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessage("Client not found with id: " + id);
            verify(clientAdapter, times(1)).getClientById(any(Long.class));
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenUpdateClientWithExistingCpf() {
            // Arrange
            Long id = 1L;
            String existingCpf = "55426210152";
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
            clientUpdateRequestDto.getCpf().setDocumentNumber(existingCpf);
            Client client = ClientHelper.createClientObject();
            client.setId(id);
            client.getAddress().setId(id);

            when(clientAdapter.getClientById(any(Long.class))).thenReturn(Optional.of(client));
            when(clientAdapter.existsClientByCpf(any(Cpf.class))).thenReturn(true);

            // Act & Assert
            assertThatThrownBy(() ->
                    clientService.updateClient(id, clientUpdateRequestDto))
                    .isInstanceOf(ClientAlreadyExistsException.class)
                    .hasMessage("Client already exists with CPF: " + existingCpf);
            verify(clientAdapter, times(1)).getClientById(any(Long.class));
            verify(clientAdapter, times(1)).existsClientByCpf(any(Cpf.class));
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenUpdateClientWithExistingEmail() {
            // Arrange
            Long id = 1L;
            String existingEmail = "existing@exists.com";
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
            clientUpdateRequestDto.setEmail(existingEmail);
            Client client = ClientHelper.createClientObject();
            client.setId(id);
            client.getAddress().setId(id);

            when(clientAdapter.getClientById(any(Long.class))).thenReturn(Optional.of(client));
            when(clientAdapter.existsClientByCpf(any(Cpf.class))).thenReturn(false);
            when(clientAdapter.existsClientByEmail(any(String.class))).thenReturn(true);

            // Act & Assert
            assertThatThrownBy(() ->
                    clientService.updateClient(id, clientUpdateRequestDto))
                    .isInstanceOf(ClientAlreadyExistsException.class)
                    .hasMessage("Client already exists with e-mail: " + existingEmail);
            verify(clientAdapter, times(1)).getClientById(any(Long.class));
            verify(clientAdapter, times(1)).existsClientByCpf(any(Cpf.class));
            verify(clientAdapter, times(1)).existsClientByEmail(any(String.class));
        }
    }

    @Nested
    class DeleteClient {

        @Test
        void shouldDeleteClientById() {
            // Arrange
            Long id = 1L;

            when(clientAdapter.existsClientById(any(Long.class))).thenReturn(true);
            doNothing().when(clientAdapter).deleteClientById(any(Long.class));

            // Act
            clientService.deleteClientById(id);

            // Assert
            verify(clientAdapter, times(1)).existsClientById(any(Long.class));
            verify(clientAdapter, times(1)).deleteClientById(any(Long.class));
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenDeleteClientByIdIsNotFound() {
            // Arrange
            Long clientId = 999L;

            when(clientAdapter.existsClientById(any(Long.class))).thenReturn(false);

            // Act
            assertThatThrownBy(() ->
                    clientService.deleteClientById(clientId))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessage("Client not found with id: " + clientId);
            verify(clientAdapter, times(1)).existsClientById(any(Long.class));
        }
    }
}
