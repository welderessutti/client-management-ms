package br.com.fiap.client_management_ms.infrastructure.adapter.internal;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.core.domain.Cpf;
import br.com.fiap.client_management_ms.core.port.out.ClientAdapter;
import br.com.fiap.client_management_ms.infrastructure.entity.ClientEntity;
import br.com.fiap.client_management_ms.infrastructure.entity.CpfEntity;
import br.com.fiap.client_management_ms.infrastructure.repository.ClientRepository;
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
import static org.mockito.Mockito.*;

class ClientAdapterImplTest {

    private ClientAdapter clientAdapter;

    @Mock
    private ClientRepository clientRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        clientAdapter = new ClientAdapterImpl(clientRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CreateClient {

        @Test
        void shouldCreateClient() {
            // Arrange
            Long id = 1L;
            Client client = ClientHelper.createClientObject();
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);
            clientEntity.getAddress().setId(id);
            Client expectedClient = ClientHelper.createClientResponseObject();
            expectedClient.setId(clientEntity.getId());

            when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

            // Act
            Client clientSaved = clientAdapter.createClient(client);

            // Assert
            assertThat(clientSaved)
                    .isNotNull()
                    .isInstanceOf(Client.class)
                    .isEqualTo(expectedClient)
                    .hasToString(expectedClient.toString())
                    .doesNotHaveSameHashCodeAs(client);
            assertThat(clientSaved.getId()).isNotNull().isEqualTo(id);
            assertThat(clientSaved.getFullName()).isNull();
            assertThat(clientSaved.getEmail()).isNull();
            assertThat(clientSaved.getMobilePhoneNumber()).isNull();
            assertThat(clientSaved.getCpf()).isNull();
            assertThat(clientSaved.getAddress()).isNull();
            verify(clientRepository, times(1)).save(any(ClientEntity.class));
        }
    }

    @Nested
    class GetClient {

        @Test
        void shouldGetClientById() {
            // Arrange
            Long id = 1L;
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);
            clientEntity.getAddress().setId(id);
            Client expectedClient = ClientHelper.createClientObject();
            expectedClient.setId(clientEntity.getId());
            expectedClient.getAddress().setId(clientEntity.getId());

            when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(clientEntity));

            // Act
            Optional<Client> clientReturned = clientAdapter.getClientById(id);

            // Assert
            assertThat(clientReturned).isPresent().isNotNull().isInstanceOf(Optional.class);
            clientReturned.ifPresent(c -> {
                assertThat(c)
                        .isNotNull()
                        .isInstanceOf(Client.class)
                        .isEqualTo(expectedClient)
                        .hasToString(expectedClient.toString());
                assertThat(c.getCpf())
                        .isNotNull()
                        .isInstanceOf(Cpf.class)
                        .isEqualTo(expectedClient.getCpf())
                        .hasToString(expectedClient.getCpf().toString());
                assertThat(c.getAddress())
                        .isNotNull()
                        .isInstanceOf(Address.class)
                        .isEqualTo(expectedClient.getAddress())
                        .hasToString(expectedClient.getAddress().toString());
                assertThat(c.getId()).isNotNull().isEqualTo(id);
                assertThat(c.getFullName()).isNotNull();
                assertThat(c.getEmail()).isNotNull();
                assertThat(c.getMobilePhoneNumber()).isNotNull();
            });
            verify(clientRepository, times(1)).findById(any(Long.class));
        }

        @Test
        void shouldReturnOptionalEmptyWhenClientNotFoundById() {
            // Arrange
            Long id = 1L;

            when(clientRepository.findById(any(Long.class))).thenReturn(Optional.empty());

            // Act
            Optional<Client> clientReturned = clientAdapter.getClientById(id);

            // Assert
            assertThat(clientReturned).isEmpty().isNotNull().isInstanceOf(Optional.class);
            verify(clientRepository, times(1)).findById(any(Long.class));
        }

        @Test
        void shouldGetClientByEmail() {
            // Arrange
            Long id = 1L;
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);
            clientEntity.getAddress().setId(id);
            String email = clientEntity.getEmail();
            Client expectedClient = ClientHelper.createClientObject();
            expectedClient.setId(clientEntity.getId());
            expectedClient.getAddress().setId(clientEntity.getId());

            when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.of(clientEntity));

            // Act
            Optional<Client> clientReturned = clientAdapter.getClientByEmail(email);

            // Assert
            assertThat(clientReturned).isPresent().isNotNull().isInstanceOf(Optional.class);
            clientReturned.ifPresent(c -> {
                assertThat(c)
                        .isNotNull()
                        .isInstanceOf(Client.class)
                        .isEqualTo(expectedClient)
                        .hasToString(expectedClient.toString());
                assertThat(c.getCpf())
                        .isNotNull()
                        .isInstanceOf(Cpf.class)
                        .isEqualTo(expectedClient.getCpf())
                        .hasToString(expectedClient.getCpf().toString());
                assertThat(c.getAddress())
                        .isNotNull()
                        .isInstanceOf(Address.class)
                        .isEqualTo(expectedClient.getAddress())
                        .hasToString(expectedClient.getAddress().toString());
                assertThat(c.getId()).isNotNull();
                assertThat(c.getFullName()).isNotNull();
                assertThat(c.getEmail()).isNotNull().isEqualTo(email);
                assertThat(c.getMobilePhoneNumber()).isNotNull();
            });
            verify(clientRepository, times(1)).findByEmail(any(String.class));
        }

        @Test
        void shouldReturnOptionalEmptyWhenClientNotFoundByEmail() {
            // Arrange
            String email = "not@exists.com";

            when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

            // Act
            Optional<Client> clientReturned = clientAdapter.getClientByEmail(email);

            // Assert
            assertThat(clientReturned).isEmpty().isNotNull().isInstanceOf(Optional.class);
            verify(clientRepository, times(1)).findByEmail(any(String.class));
        }

        @Test
        void shouldGetAllClients() {
            // Arrange
            ClientEntity clientEntity1 = ClientHelper.createClientEntityObject();
            ClientEntity clientEntity2 = ClientHelper.createClientEntityObject();
            List<ClientEntity> clientEntityList = Arrays.asList(clientEntity1, clientEntity2);

            when(clientRepository.findAll()).thenReturn(clientEntityList);

            // Act
            List<Client> clientList = clientAdapter.getAllClients();

            // Assert
            assertThat(clientList)
                    .isNotNull()
                    .hasSize(2)
                    .isExactlyInstanceOf(ArrayList.class)
                    .allSatisfy(client -> {
                        assertThat(client)
                                .isNotNull()
                                .isInstanceOf(Client.class);
                    });
            verify(clientRepository, times(1)).findAll();
        }
    }

    @Nested
    class UpdateClient {

        @Test
        void shouldUpdateClient() {
            // Arrange
            Long id = 1L;
            Client client = ClientHelper.createClientObject();
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);
            clientEntity.getAddress().setId(id);

            when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

            // Act
            Client updatedClient = clientAdapter.updateClient(client);

            // Assert
            assertThat(updatedClient).isNotNull().isInstanceOf(Client.class);
            assertThat(updatedClient.getId()).isNotNull().isEqualTo(id);
            assertThat(updatedClient.getFullName()).isNotNull().isEqualTo(client.getFullName());
            assertThat(updatedClient.getEmail()).isNotNull().isEqualTo(client.getEmail());
            assertThat(updatedClient.getMobilePhoneNumber()).isNotNull().isEqualTo(client.getMobilePhoneNumber());
            assertThat(updatedClient.getCpf().getDocumentNumber()).isNotNull().isEqualTo(client.getCpf().getDocumentNumber());
            assertThat(updatedClient.getAddress().getId()).isNotNull().isEqualTo(id);
            verify(clientRepository, times(1)).save(any(ClientEntity.class));
        }
    }

    @Nested
    class DeleteClient {

        @Test
        void shouldDeleteClientById() {
            // Arrange
            Long id = 1L;
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);

            doNothing().when(clientRepository).deleteById(any(Long.class));

            // Act
            clientAdapter.deleteClientById(id);

            // Assert
            verify(clientRepository, times(1)).deleteById(any(Long.class));
        }
    }

    @Nested
    class VerifyIfExistsClient {

        @Test
        void shouldVerifyIfExistsClientById() {
            // Arrange
            Long id = 1L;
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);

            when(clientRepository.existsById(any(Long.class))).thenReturn(true);

            // Act
            boolean clientExists = clientAdapter.existsClientById(id);

            // Assert
            assertThat(clientExists).isTrue();
            verify(clientRepository, times(1)).existsById(any(Long.class));
        }

        @Test
        void shouldReturnFalseWhenVerifyIfExistsClientByIdAndClientDoesNotExist() {
            // Arrange
            Long id = 999L;

            when(clientRepository.existsById(any(Long.class))).thenReturn(false);

            // Act
            boolean clientExists = clientAdapter.existsClientById(id);

            // Assert
            assertThat(clientExists).isFalse();
            verify(clientRepository, times(1)).existsById(any(Long.class));
        }

        @Test
        void shouldVerifyIfExistsClientByEmail() {
            // Arrange
            Long id = 1L;
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);
            String email = clientEntity.getEmail();

            when(clientRepository.existsByEmail(any(String.class))).thenReturn(true);

            // Act
            boolean clientExists = clientAdapter.existsClientByEmail(email);

            // Assert
            assertThat(clientExists).isTrue();
            verify(clientRepository, times(1)).existsByEmail(any(String.class));
        }

        @Test
        void shouldReturnFalseWhenVerifyIfExistsClientByEmailAndClientDoesNotExist() {
            // Arrange
            String email = "not@exists.com";

            when(clientRepository.existsByEmail(any(String.class))).thenReturn(false);

            // Act
            boolean clientExists = clientAdapter.existsClientByEmail(email);

            // Assert
            assertThat(clientExists).isFalse();
            verify(clientRepository, times(1)).existsByEmail(any(String.class));
        }

        @Test
        void shouldVerifyIfExistsClientByCpf() {
            // Arrange
            Long id = 1L;
            Client client = ClientHelper.createClientObject();
            client.setId(id);
            Cpf cpf = client.getCpf();

            when(clientRepository.existsByCpf(any(CpfEntity.class))).thenReturn(true);

            // Act
            boolean clientExists = clientAdapter.existsClientByCpf(cpf);

            // Assert
            assertThat(clientExists).isTrue();
            verify(clientRepository, times(1)).existsByCpf(any(CpfEntity.class));
        }

        @Test
        void shouldReturnFalseWhenVerifyIfExistsClientByCpfAndClientDoesNotExist() {
            // Arrange
            Long id = 1L;
            Client client = ClientHelper.createClientObject();
            client.setId(id);
            Cpf cpf = client.getCpf();

            when(clientRepository.existsByCpf(any(CpfEntity.class))).thenReturn(false);

            // Act
            boolean clientExists = clientAdapter.existsClientByCpf(cpf);

            // Assert
            assertThat(clientExists).isFalse();
            verify(clientRepository, times(1)).existsByCpf(any(CpfEntity.class));
        }
    }
}
