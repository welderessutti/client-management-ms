package br.com.fiap.client_management_ms.infrastructure.adapter.internal;

import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.core.domain.Cpf;
import br.com.fiap.client_management_ms.core.port.out.ClientAdapter;
import br.com.fiap.client_management_ms.utils.ClientHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class ClientAdapterImplIT {

    @Autowired
    private ClientAdapter clientAdapter;

    @Nested
    class CreateClient {

        @Test
        void shouldCreateClient() {
            // Arrange
            Client client = ClientHelper.createClientObject();
            Client expectedClient = ClientHelper.createClientResponseObject();
            expectedClient.setId(client.getId());

            // Act
            Client clientSaved = clientAdapter.createClient(client);

            // Assert
            assertThat(clientSaved)
                    .isNotNull()
                    .isInstanceOf(Client.class)
                    .doesNotHaveSameHashCodeAs(client);
            assertThat(clientSaved.getId()).isNotNull();
            assertThat(clientSaved.getFullName()).isNull();
            assertThat(clientSaved.getEmail()).isNull();
            assertThat(clientSaved.getMobilePhoneNumber()).isNull();
            assertThat(clientSaved.getCpf()).isNull();
            assertThat(clientSaved.getAddress()).isNull();
        }
    }

    @Nested
    class GetClient {

        @Test
        void shouldGetClientById() {
            // Arrange
            Long id = 1L;

            // Act
            Optional<Client> clientReturned = clientAdapter.getClientById(id);

            // Assert
            assertThat(clientReturned).isPresent().isNotNull().isInstanceOf(Optional.class);
            clientReturned.ifPresent(c -> {
                assertThat(c).isNotNull().isInstanceOf(Client.class);
                assertThat(c.getId()).isNotNull().isEqualTo(id);
                assertThat(c.getFullName()).isNotNull();
                assertThat(c.getEmail()).isNotNull();
                assertThat(c.getMobilePhoneNumber()).isNotNull();
                assertThat(c.getCpf()).isNotNull();
                assertThat(c.getAddress()).isNotNull();
            });
        }

        @Test
        void shouldReturnOptionalEmptyWhenClientNotFoundById() {
            // Arrange
            Long id = 10L;

            // Act
            Optional<Client> clientReturned = clientAdapter.getClientById(id);

            // Assert
            assertThat(clientReturned).isEmpty().isNotNull().isInstanceOf(Optional.class);
        }

        @Test
        void shouldGetClientByEmail() {
            // Arrange
            String email = "joao@silva.com.br";

            // Act
            Optional<Client> clientReturned = clientAdapter.getClientByEmail(email);

            // Assert
            assertThat(clientReturned).isPresent().isNotNull().isInstanceOf(Optional.class);
            clientReturned.ifPresent(c -> {
                assertThat(c).isNotNull().isInstanceOf(Client.class);
                assertThat(c.getId()).isNotNull();
                assertThat(c.getFullName()).isNotNull();
                assertThat(c.getEmail()).isNotNull().isEqualTo(email);
                assertThat(c.getMobilePhoneNumber()).isNotNull();
                assertThat(c.getCpf()).isNotNull();
                assertThat(c.getAddress()).isNotNull();
            });
        }

        @Test
        void shouldReturnOptionalEmptyWhenClientNotFoundByEmail() {
            // Arrange
            String email = "not@exists.com";

            // Act
            Optional<Client> clientReturned = clientAdapter.getClientByEmail(email);

            // Assert
            assertThat(clientReturned).isEmpty().isNotNull().isInstanceOf(Optional.class);
        }

        @Test
        void shouldGetAllClients() {
            // Act
            List<Client> clientList = clientAdapter.getAllClients();

            // Assert
            assertThat(clientList)
                    .isNotNull()
                    .hasSize(6)
                    .isExactlyInstanceOf(ArrayList.class)
                    .allSatisfy(client -> {
                        assertThat(client)
                                .isNotNull()
                                .isInstanceOf(Client.class);
                    });
        }
    }

    @Nested
    class UpdateClient {

        @Test
        void shouldUpdateClient() {
            // Arrange
            Long id = 1L;
            Client client = ClientHelper.createClientObject();
            client.setId(id);
            client.getAddress().setId(id);
            client.getAddress().setClient(client);

            // Act
            Client updatedClient = clientAdapter.updateClient(client);

            // Assert
            assertThat(updatedClient).isNotNull().isInstanceOf(Client.class);
            assertThat(updatedClient.getId()).isNotNull().isEqualTo(id);
            assertThat(updatedClient.getFullName()).isNotNull().isEqualTo(client.getFullName());
            assertThat(updatedClient.getEmail()).isNotNull().isEqualTo(client.getEmail());
            assertThat(updatedClient.getMobilePhoneNumber()).isNotNull().isEqualTo(client.getMobilePhoneNumber());
            assertThat(updatedClient.getCpf().getDocumentNumber()).isNotNull().isEqualTo(client.getCpf().getDocumentNumber());
            assertThat(updatedClient.getAddress().getId()).isNotNull().isEqualTo(client.getAddress().getId());
        }
    }

    @Nested
    class DeleteClient {

        @Test
        void shouldDeleteClientById() {
            // Arrange
            Long id = 1L;

            // Act
            clientAdapter.deleteClientById(id);

            // Assert
            assertThat(clientAdapter.existsClientById(id)).isFalse();
        }
    }

    @Nested
    class VerifyIfExistsClient {

        @Test
        void shouldVerifyIfExistsClientById() {
            // Arrange
            Long id = 1L;

            // Act
            boolean clientExists = clientAdapter.existsClientById(id);

            // Assert
            assertThat(clientExists).isTrue();
        }

        @Test
        void shouldReturnFalseWhenVerifyIfExistsClientByIdAndClientDoesNotExist() {
            // Arrange
            Long id = 999L;

            // Act
            boolean clientExists = clientAdapter.existsClientById(id);

            // Assert
            assertThat(clientExists).isFalse();
        }

        @Test
        void shouldVerifyIfExistsClientByEmail() {
            // Arrange
            String email = "joao@silva.com.br";

            // Act
            boolean clientExists = clientAdapter.existsClientByEmail(email);

            // Assert
            assertThat(clientExists).isTrue();
        }

        @Test
        void shouldReturnFalseWhenVerifyIfExistsClientByEmailAndClientDoesNotExist() {
            // Arrange
            String email = "not@exists.com";

            // Act
            boolean clientExists = clientAdapter.existsClientByEmail(email);

            // Assert
            assertThat(clientExists).isFalse();
        }

        @Test
        void shouldVerifyIfExistsClientByCpf() {
            // Arrange
            Cpf cpf = new Cpf("55426210152");

            // Act
            boolean clientExists = clientAdapter.existsClientByCpf(cpf);

            // Assert
            assertThat(clientExists).isTrue();
        }

        @Test
        void shouldReturnFalseWhenVerifyIfExistsClientByCpfAndClientDoesNotExist() {
            // Arrange
            Cpf cpf = new Cpf("13184731014");

            // Act
            boolean clientExists = clientAdapter.existsClientByCpf(cpf);

            // Assert
            assertThat(clientExists).isFalse();
        }
    }
}
