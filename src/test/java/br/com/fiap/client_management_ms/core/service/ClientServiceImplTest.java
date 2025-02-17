//package br.com.fiap.client_management_ms.core.service;
//
//import br.com.fiap.client_management_ms.core.domain.Address;
//import br.com.fiap.client_management_ms.core.domain.Client;
//import br.com.fiap.client_management_ms.core.domain.Cpf;
//import br.com.fiap.client_management_ms.core.exception.ClientAlreadyExistsException;
//import br.com.fiap.client_management_ms.core.exception.ClientNotFoundException;
//import br.com.fiap.client_management_ms.core.exception.InvalidCpfDocumentNumberException;
//import br.com.fiap.client_management_ms.core.port.in.ClientPortIn;
//import br.com.fiap.client_management_ms.core.port.out.ClientPortOut;
//import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
//import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
//import br.com.fiap.client_management_ms.framework.dto.response.AllClientsResponseDto;
//import br.com.fiap.client_management_ms.framework.dto.response.ClientResponseDto;
//import br.com.fiap.client_management_ms.framework.dto.response.CreateClientResponseDto;
//import br.com.fiap.client_management_ms.utils.ClientHelper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//class ClientServiceImplTest {
//
//    private ClientPortIn clientPortIn;
//
//    @Mock
//    private ClientPortOut clientPortOut;
//
//    @Mock
//    private AddressService addressService;
//
//    @Mock
//    private Cpf cpf;
//
//    private AutoCloseable openMocks;
//
//    @BeforeEach
//    public void setUp() {
//        openMocks = MockitoAnnotations.openMocks(this);
//        clientPortIn = new ClientServiceImpl(addressService, clientPortOut);
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        openMocks.close();
//    }
//
//    @Nested
//    class CreateClient {
//
//        @Test
//        void shouldCreateClient() {
//            // Arrange
//            Long id = 1L;
//            Client client = ClientHelper.createClientObject();
//            client.setId(id);
//            Address address = client.getAddress();
//            address.setId(id);
//            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
//
//            when(clientPortOut.existsClientByCpf(any(Cpf.class))).thenReturn(false);
//            when(clientPortOut.existsClientByEmail(any(String.class))).thenReturn(false);
//            when(addressService.getAddressByApi(any(Address.class))).thenReturn(address);
//            when(clientPortOut.createClient(any(Client.class))).thenReturn(client);
//
//            // Act
//            CreateClientResponseDto createdClientResponseDto = clientPortIn.createClient(clientCreateRequestDto);
//
//            // Assert
//            assertThat(createdClientResponseDto).isNotNull().isInstanceOf(CreateClientResponseDto.class);
//            assertThat(createdClientResponseDto.getId()).isNotNull();
//            verify(clientPortOut, times(1)).existsClientByCpf(any(Cpf.class));
//            verify(clientPortOut, times(1)).existsClientByEmail(any(String.class));
//            verify(addressService, times(1)).getAddressByApi(any(Address.class));
//            verify(clientPortOut, times(1)).createClient(any(Client.class));
//        }
//
//        @Test
//        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingCpf() {
//            // Arrange
//            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
//            Client client = ClientHelper.createClientObject();
//
//            when(clientPortOut.existsClientByCpf(any(Cpf.class))).thenReturn(true);
//
//            // Act & Assert
//            assertThatThrownBy(() -> clientPortIn.createClient(clientCreateRequestDto))
//                    .isInstanceOf(ClientAlreadyExistsException.class)
//                    .hasMessage("Client already exists with CPF: " + client.getCpf().getDocumentNumber());
//            verify(clientPortOut, times(1)).existsClientByCpf(any(Cpf.class));
//        }
//
//        @Test
//        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingEmail() {
//            // Arrange
//            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
//            Client client = ClientHelper.createClientObject();
//
//            when(clientPortOut.existsClientByEmail(any(String.class))).thenReturn(true);
//
//            // Act & Assert
//            assertThatThrownBy(() -> clientPortIn.createClient(clientCreateRequestDto))
//                    .isInstanceOf(ClientAlreadyExistsException.class)
//                    .hasMessage("Client already exists with e-mail: " + client.getEmail());
//            verify(clientPortOut, times(1)).existsClientByEmail(any(String.class));
//        }
//
//        @Test
//        void shouldThrowInvalidCpfDocumentNumberExceptionWhenCreateClientWithInvalidCpf() {
//            // Arrange
//            String invalidCpf = "999.999.999-99";
//            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
//            clientCreateRequestDto.getCpf().setDocumentNumber(invalidCpf);
//
//            // Act & Assert
//            assertThatThrownBy(() -> clientPortIn.createClient(clientCreateRequestDto))
//                    .isInstanceOf(InvalidCpfDocumentNumberException.class)
//                    .hasMessage("Invalid CPF document number: 99999999999");
//        }
//    }
//
//    @Nested
//    class GetClient {
//
//        @Test
//        void shouldGetClientById() {
//            // Arrange
//            Long id = 1L;
//            Client client = ClientHelper.createClientObject();
//            client.setId(id);
//
//            when(clientPortOut.getClientById(any(Long.class))).thenReturn(Optional.of(client));
//
//            // Act
//            ClientResponseDto foundClientResponseDto = clientPortIn.getClientById(id);
//
//            // Assert
//            assertThat(foundClientResponseDto).isNotNull().isInstanceOf(ClientResponseDto.class);
//            assertThat(foundClientResponseDto.getId()).isNotNull().isEqualTo(id);
//            assertThat(foundClientResponseDto.getFullName()).isNotNull();
//            assertThat(foundClientResponseDto.getEmail()).isNotNull();
//            assertThat(foundClientResponseDto.getMobilePhoneNumber()).isNotNull();
//            assertThat(foundClientResponseDto.getCpf()).isNotNull();
//            assertThat(foundClientResponseDto.getAddress()).isNotNull();
//            verify(clientPortOut, times(1)).getClientById(any(Long.class));
//        }
//
//        @Test
//        void shouldThrowClientNotFoundExceptionWhenGetClientByIdIsNotFound() {
//            // Arrange
//            Long clientId = 1L;
//
//            when(clientPortOut.getClientById(any(Long.class))).thenReturn(Optional.empty());
//
//            // Act & Assert
//            assertThatThrownBy(() ->
//                    clientPortIn.getClientById(clientId))
//                    .isInstanceOf(ClientNotFoundException.class)
//                    .hasMessage("Client not found with id: " + clientId);
//            verify(clientPortOut, times(1)).getClientById(any(Long.class));
//        }
//
//        @Test
//        void shouldGetClientByEmail() {
//            // Arrange
//            Long id = 1L;
//            Client client = ClientHelper.createClientObject();
//            client.setId(id);
//            String email = "marcos@silva.com";
//
//            when(clientPortOut.getClientByEmail(any(String.class))).thenReturn(Optional.of(client));
//
//            // Act
//            ClientResponseDto foundClientResponseDto = clientPortIn.getClientByEmail(email);
//
//            // Assert
//            assertThat(foundClientResponseDto).isNotNull().isInstanceOf(ClientResponseDto.class);
//            assertThat(foundClientResponseDto.getId()).isNotNull();
//            assertThat(foundClientResponseDto.getFullName()).isNotNull();
//            assertThat(foundClientResponseDto.getEmail()).isNotNull().isEqualTo(email);
//            assertThat(foundClientResponseDto.getMobilePhoneNumber()).isNotNull();
//            assertThat(foundClientResponseDto.getCpf()).isNotNull();
//            assertThat(foundClientResponseDto.getAddress()).isNotNull();
//            verify(clientPortOut, times(1)).getClientByEmail(any(String.class));
//        }
//
//        @Test
//        void shouldThrowClientNotFoundExceptionWhenGetClientByEmailIsNotFound() {
//            // Arrange
//            String email = "not@exists.com";
//
//            when(clientPortOut.getClientById(any(Long.class))).thenReturn(Optional.empty());
//
//            // Act & Assert
//            assertThatThrownBy(() ->
//                    clientPortIn.getClientByEmail(email))
//                    .isInstanceOf(ClientNotFoundException.class)
//                    .hasMessage("Client not found with e-mail: " + email);
//            verify(clientPortOut, times(1)).getClientByEmail(any(String.class));
//        }
//
//        @Test
//        void shouldGetAllClients() {
//            // Arrange
//            Client client1 = ClientHelper.createClientObject();
//            Client client2 = ClientHelper.createClientObject();
//            List<Client> clientList = Arrays.asList(client1, client2);
//
//            when(clientPortOut.getAllClients()).thenReturn(clientList);
//
//            // Act
//            AllClientsResponseDto allClientsResponseDto = clientPortIn.getAllClients();
//
//            // Assert
//            assertThat(allClientsResponseDto).isNotNull().isInstanceOf(AllClientsResponseDto.class);
//            assertThat(allClientsResponseDto.getClients())
//                    .isNotNull()
//                    .isExactlyInstanceOf(ArrayList.class)
//                    .allSatisfy(client -> {
//                        assertThat(client)
//                                .isNotNull()
//                                .isInstanceOf(ClientResponseDto.class);
//                    });
//            verify(clientPortOut, times(1)).getAllClients();
//        }
//    }
//
//    @Nested
//    class UpdateClient {
//
//        @Test
//        void shouldUpdateClient() {
//            // Arrange
//            Long id = 1L;
//            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
//            Address updatedAddress = ClientHelper.createUpdatedAddressObject();
//            Client client = ClientHelper.createClientObject();
//            client.setId(id);
//            client.getAddress().setId(id);
//            Client updatedClient = ClientHelper.createUpdatedClientObject();
//            updatedClient.setId(id);
//            updatedClient.getAddress().setId(id);
//
//            when(clientPortOut.getClientById(any(Long.class))).thenReturn(Optional.of(client));
//            when(clientPortOut.existsClientByCpf(any(Cpf.class))).thenReturn(false);
//            when(clientPortOut.existsClientByEmail(any(String.class))).thenReturn(false);
//            when(addressService.getAddressByApi(any(Address.class))).thenReturn(updatedAddress);
//            when(clientPortOut.updateClient(any(Client.class))).thenReturn(updatedClient);
//
//            // Act
//            ClientResponseDto updatedClientResponseDto = clientPortIn.updateClient(id, clientUpdateRequestDto);
//
//            // Assert
//            assertThat(updatedClientResponseDto).isNotNull().isInstanceOf(ClientResponseDto.class);
//            assertThat(updatedClientResponseDto.getId()).isNotNull().isEqualTo(id);
//            assertThat(updatedClientResponseDto.getFullName()).isNotNull().isEqualTo(clientUpdateRequestDto.getFullName());
//            assertThat(updatedClientResponseDto.getEmail()).isNotNull().isEqualTo(clientUpdateRequestDto.getEmail());
//            assertThat(updatedClientResponseDto.getMobilePhoneNumber()).isNotNull().isEqualTo(clientUpdateRequestDto.getMobilePhoneNumber());
//            assertThat(updatedClientResponseDto.getCpf().getDocumentNumber()).isNotNull().isEqualTo(clientUpdateRequestDto.getCpf().getDocumentNumber());
//            assertThat(updatedClientResponseDto.getAddress().getId()).isNotNull().isEqualTo(id);
//            verify(clientPortOut, times(1)).getClientById(any(Long.class));
//            verify(clientPortOut, times(1)).existsClientByCpf(any(Cpf.class));
//            verify(clientPortOut, times(1)).existsClientByEmail(any(String.class));
//            verify(addressService, times(1)).getAddressByApi(any(Address.class));
//            verify(clientPortOut, times(1)).updateClient(any(Client.class));
//        }
//
//        @Test
//        void shouldThrowClientNotFoundExceptionWhenUpdateClientWithNotFoundId() {
//            // Arrange
//            Long id = 999L;
//            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
//
//            when(clientPortOut.getClientById(any(Long.class))).thenReturn(Optional.empty());
//
//            // Act & Assert
//            assertThatThrownBy(() ->
//                    clientPortIn.updateClient(id, clientUpdateRequestDto))
//                    .isInstanceOf(ClientNotFoundException.class)
//                    .hasMessage("Client not found with id: " + id);
//            verify(clientPortOut, times(1)).getClientById(any(Long.class));
//        }
//
//        @Test
//        void shouldThrowClientAlreadyExistsExceptionWhenUpdateClientWithExistingCpf() {
//            // Arrange
//            Long id = 1L;
//            String existingCpf = "55426210152";
//            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
//            clientUpdateRequestDto.getCpf().setDocumentNumber(existingCpf);
//            Client client = ClientHelper.createClientObject();
//            client.setId(id);
//            client.getAddress().setId(id);
//
//            when(clientPortOut.getClientById(any(Long.class))).thenReturn(Optional.of(client));
//            when(clientPortOut.existsClientByCpf(any(Cpf.class))).thenReturn(true);
//
//            // Act & Assert
//            assertThatThrownBy(() ->
//                    clientPortIn.updateClient(id, clientUpdateRequestDto))
//                    .isInstanceOf(ClientAlreadyExistsException.class)
//                    .hasMessage("Client already exists with CPF: " + existingCpf);
//            verify(clientPortOut, times(1)).getClientById(any(Long.class));
//            verify(clientPortOut, times(1)).existsClientByCpf(any(Cpf.class));
//        }
//
//        @Test
//        void shouldThrowClientAlreadyExistsExceptionWhenUpdateClientWithExistingEmail() {
//            // Arrange
//            Long id = 1L;
//            String existingEmail = "existing@exists.com";
//            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
//            clientUpdateRequestDto.setEmail(existingEmail);
//            Client client = ClientHelper.createClientObject();
//            client.setId(id);
//            client.getAddress().setId(id);
//
//            when(clientPortOut.getClientById(any(Long.class))).thenReturn(Optional.of(client));
//            when(clientPortOut.existsClientByCpf(any(Cpf.class))).thenReturn(false);
//            when(clientPortOut.existsClientByEmail(any(String.class))).thenReturn(true);
//
//            // Act & Assert
//            assertThatThrownBy(() ->
//                    clientPortIn.updateClient(id, clientUpdateRequestDto))
//                    .isInstanceOf(ClientAlreadyExistsException.class)
//                    .hasMessage("Client already exists with e-mail: " + existingEmail);
//            verify(clientPortOut, times(1)).getClientById(any(Long.class));
//            verify(clientPortOut, times(1)).existsClientByCpf(any(Cpf.class));
//            verify(clientPortOut, times(1)).existsClientByEmail(any(String.class));
//        }
//    }
//
//    @Nested
//    class DeleteClient {
//
//        @Test
//        void shouldDeleteClientById() {
//            // Arrange
//            Long id = 1L;
//
//            when(clientPortOut.existsClientById(any(Long.class))).thenReturn(true);
//            doNothing().when(clientPortOut).deleteClientById(any(Long.class));
//
//            // Act
//            clientPortIn.deleteClientById(id);
//
//            // Assert
//            verify(clientPortOut, times(1)).existsClientById(any(Long.class));
//            verify(clientPortOut, times(1)).deleteClientById(any(Long.class));
//        }
//
//        @Test
//        void shouldThrowClientNotFoundExceptionWhenDeleteClientByIdIsNotFound() {
//            // Arrange
//            Long clientId = 999L;
//
//            when(clientPortOut.existsClientById(any(Long.class))).thenReturn(false);
//
//            // Act
//            assertThatThrownBy(() ->
//                    clientPortIn.deleteClientById(clientId))
//                    .isInstanceOf(ClientNotFoundException.class)
//                    .hasMessage("Client not found with id: " + clientId);
//            verify(clientPortOut, times(1)).existsClientById(any(Long.class));
//        }
//    }
//}
