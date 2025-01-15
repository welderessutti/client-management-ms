package br.com.fiap.client_management_ms.core.service;

import br.com.fiap.client_management_ms.core.exception.ClientAlreadyExistsException;
import br.com.fiap.client_management_ms.core.exception.ClientNotFoundException;
import br.com.fiap.client_management_ms.core.exception.InvalidCpfDocumentNumberException;
import br.com.fiap.client_management_ms.core.port.in.ClientPortIn;
import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.create.CpfCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.AllClientsResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.ClientResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.CreateClientResponseDto;
import br.com.fiap.client_management_ms.utils.ClientHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class ClientServiceImplIT {

    @Autowired
    private ClientPortIn clientPortIn;

    @Nested
    class CreateClient {

        @Test
        void shouldCreateClient() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();

            // Act
            CreateClientResponseDto createdClientResponseDto = clientPortIn.createClient(clientCreateRequestDto);

            // Assert
            assertThat(createdClientResponseDto).isNotNull().isInstanceOf(CreateClientResponseDto.class);
            assertThat(createdClientResponseDto.getId()).isNotNull();
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingCpf() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.setCpf(new CpfCreateRequestDto("55426210152"));

            // Act & Assert
            assertThatThrownBy(() -> clientPortIn.createClient(clientCreateRequestDto))
                    .isInstanceOf(ClientAlreadyExistsException.class)
                    .hasMessage("Client already exists with CPF: " + clientCreateRequestDto.getCpf().getDocumentNumber());
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingEmail() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.setEmail("joao@silva.com.br");

            // Act & Assert
            assertThatThrownBy(() -> clientPortIn.createClient(clientCreateRequestDto))
                    .isInstanceOf(ClientAlreadyExistsException.class)
                    .hasMessage("Client already exists with e-mail: " + clientCreateRequestDto.getEmail());
        }

        @Test
        void shouldThrowInvalidCpfDocumentNumberExceptionWhenCreateClientWithInvalidCpf() {
            // Arrange
            String invalidCpf = "999.999.999-99";
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.getCpf().setDocumentNumber(invalidCpf);

            // Act & Assert
            assertThatThrownBy(() -> clientPortIn.createClient(clientCreateRequestDto))
                    .isInstanceOf(InvalidCpfDocumentNumberException.class)
                    .hasMessage("Invalid CPF document number: 99999999999");
        }
    }

    @Nested
    class GetClient {

        @Test
        void shouldGetClientById() {
            // Arrange
            Long clientId = 1L;

            // Act
            ClientResponseDto foundClientResponseDto = clientPortIn.getClientById(clientId);

            // Assert
            assertThat(foundClientResponseDto).isNotNull().isInstanceOf(ClientResponseDto.class);
            assertThat(foundClientResponseDto.getId()).isNotNull().isEqualTo(clientId);
            assertThat(foundClientResponseDto.getFullName()).isNotNull();
            assertThat(foundClientResponseDto.getEmail()).isNotNull();
            assertThat(foundClientResponseDto.getMobilePhoneNumber()).isNotNull();
            assertThat(foundClientResponseDto.getCpf()).isNotNull();
            assertThat(foundClientResponseDto.getAddress()).isNotNull();
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenGetClientByIdIsNotFound() {
            // Arrange
            Long clientId = 10L;

            // Act & Assert
            assertThatThrownBy(() ->
                    clientPortIn.getClientById(clientId))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessage("Client not found with id: " + clientId);
        }

        @Test
        void shouldGetClientByEmail() {
            // Arrange
            String email = "joao@silva.com.br";

            // Act
            ClientResponseDto foundClientResponseDto = clientPortIn.getClientByEmail(email);

            // Assert
            assertThat(foundClientResponseDto).isNotNull().isInstanceOf(ClientResponseDto.class);
            assertThat(foundClientResponseDto.getId()).isNotNull();
            assertThat(foundClientResponseDto.getFullName()).isNotNull();
            assertThat(foundClientResponseDto.getEmail()).isNotNull().isEqualTo(email);
            assertThat(foundClientResponseDto.getMobilePhoneNumber()).isNotNull();
            assertThat(foundClientResponseDto.getCpf()).isNotNull();
            assertThat(foundClientResponseDto.getAddress()).isNotNull();
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenGetClientByEmailIsNotFound() {
            // Arrange
            String email = "not@exists.com";

            // Act & Assert
            assertThatThrownBy(() ->
                    clientPortIn.getClientByEmail(email))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessage("Client not found with e-mail: " + email);
        }

        @Test
        void shouldGetAllClients() {
            // Act
            AllClientsResponseDto allClientsResponseDto = clientPortIn.getAllClients();

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
        }
    }

    @Nested
    class UpdateClient {

        @Test
        void shouldUpdateClient() {
            // Arrange
            Long clientId = 1L;
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();

            // Act
            ClientResponseDto updatedClientResponseDto = clientPortIn.updateClient(clientId, clientUpdateRequestDto);

            // Assert
            assertThat(updatedClientResponseDto)
                    .isNotNull().isInstanceOf(ClientResponseDto.class);
            assertThat(updatedClientResponseDto.getId())
                    .isNotNull().isEqualTo(clientId);
            assertThat(updatedClientResponseDto.getFullName())
                    .isNotNull().isEqualTo(clientUpdateRequestDto.getFullName());
            assertThat(updatedClientResponseDto.getEmail())
                    .isNotNull().isEqualTo(clientUpdateRequestDto.getEmail());
            assertThat(updatedClientResponseDto.getMobilePhoneNumber())
                    .isNotNull().isEqualTo(clientUpdateRequestDto.getMobilePhoneNumber());
            assertThat(updatedClientResponseDto.getCpf().getDocumentNumber())
                    .isNotNull().isEqualTo(clientUpdateRequestDto.getCpf().getDocumentNumber());
            assertThat(updatedClientResponseDto.getAddress())
                    .isNotNull();
            assertThat(updatedClientResponseDto.getAddress().getId())
                    .isNotNull().isEqualTo(clientId);
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenUpdateClientWithNotFoundId() {
            // Arrange
            Long id = 999L;
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();

            // Act & Assert
            assertThatThrownBy(() ->
                    clientPortIn.updateClient(id, clientUpdateRequestDto))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessage("Client not found with id: " + id);
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenUpdateClientWithExistingCpf() {
            // Arrange
            Long id = 1L;
            String existingCpf = "55426210152";
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
            clientUpdateRequestDto.getCpf().setDocumentNumber(existingCpf);

            // Act & Assert
            assertThatThrownBy(() ->
                    clientPortIn.updateClient(id, clientUpdateRequestDto))
                    .isInstanceOf(ClientAlreadyExistsException.class)
                    .hasMessage("Client already exists with CPF: " + existingCpf);
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenUpdateClientWithExistingEmail() {
            // Arrange
            Long id = 1L;
            String existingEmail = "joao@silva.com.br";
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
            clientUpdateRequestDto.setEmail(existingEmail);

            // Act & Assert
            assertThatThrownBy(() ->
                    clientPortIn.updateClient(id, clientUpdateRequestDto))
                    .isInstanceOf(ClientAlreadyExistsException.class)
                    .hasMessage("Client already exists with e-mail: " + existingEmail);
        }
    }

    @Nested
    class DeleteClient {

        @Test
        void shouldDeleteClientById() {
            // Arrange
            Long clientId = 1L;

            // Act
            clientPortIn.deleteClientById(clientId);

            // Assert
            assertThatThrownBy(() -> clientPortIn.getClientById(clientId))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessage("Client not found with id: " + clientId);
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenDeleteClientByIdIsNotFound() {
            // Arrange
            Long clientId = 99L;

            // Act
            assertThatThrownBy(() ->
                    clientPortIn.deleteClientById(clientId))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessage("Client not found with id: " + clientId);
        }
    }
}
