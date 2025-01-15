package br.com.fiap.client_management_ms.framework;

import br.com.fiap.client_management_ms.core.exception.CepAddressNotFoundException;
import br.com.fiap.client_management_ms.core.exception.ClientAlreadyExistsException;
import br.com.fiap.client_management_ms.core.exception.ClientNotFoundException;
import br.com.fiap.client_management_ms.core.exception.InvalidCpfDocumentNumberException;
import br.com.fiap.client_management_ms.core.port.in.ClientPortIn;
import br.com.fiap.client_management_ms.framework.advice.ApplicationExceptionHandler;
import br.com.fiap.client_management_ms.framework.controller.ClientController;
import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.AllClientsResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.ClientResponseDto;
import br.com.fiap.client_management_ms.framework.dto.response.CreateClientResponseDto;
import br.com.fiap.client_management_ms.utils.ClientHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClientControllerTest {

    MockMvc mockMvc;

    AutoCloseable openMocks;

    ClientController clientController;

    ApplicationExceptionHandler applicationExceptionHandler;

    @Mock
    ClientPortIn clientPortIn;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        clientController = new ClientController(clientPortIn);
        applicationExceptionHandler = new ApplicationExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setControllerAdvice(applicationExceptionHandler)
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CreateClient {

        @Test
        void shouldCreateClient() throws Exception {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            CreateClientResponseDto createClientResponseDto = ClientHelper.createCreateClientResponseDtoObject();

            when(clientPortIn.createClient(any(ClientCreateRequestDto.class))).thenReturn(createClientResponseDto);

            // Act & Assert
            mockMvc.perform(post("/client")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(clientCreateRequestDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(asJsonString(createClientResponseDto)));
            verify(clientPortIn, times(1)).createClient(any(ClientCreateRequestDto.class));
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingCpf() throws Exception {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();

            when(clientPortIn.createClient(any(ClientCreateRequestDto.class)))
                    .thenThrow(new ClientAlreadyExistsException(
                            "Client already exists with CPF: " + clientCreateRequestDto.getCpf().getDocumentNumber()));

            // Act & Assert
            mockMvc.perform(post("/client")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(clientCreateRequestDto)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.timestamp")
                            .isNotEmpty())
                    .andExpect(jsonPath("$.status")
                            .value(HttpStatus.CONFLICT.value()))
                    .andExpect(jsonPath("$.error")
                            .value(HttpStatus.CONFLICT.getReasonPhrase()))
                    .andExpect(jsonPath("$.message")
                            .value("Client already exists with CPF: "
                                    + clientCreateRequestDto.getCpf().getDocumentNumber()))
                    .andExpect(jsonPath("$.path")
                            .value("/client"));
            verify(clientPortIn, times(1)).createClient(any(ClientCreateRequestDto.class));
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingEmail() throws Exception {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();

            when(clientPortIn.createClient(any(ClientCreateRequestDto.class)))
                    .thenThrow(new ClientAlreadyExistsException(
                            "Client already exists with e-mail: " + clientCreateRequestDto.getEmail()));

            // Act & Assert
            mockMvc.perform(post("/client")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(clientCreateRequestDto)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.timestamp")
                            .isNotEmpty())
                    .andExpect(jsonPath("$.status")
                            .value(HttpStatus.CONFLICT.value()))
                    .andExpect(jsonPath("$.error")
                            .value(HttpStatus.CONFLICT.getReasonPhrase()))
                    .andExpect(jsonPath("$.message")
                            .value("Client already exists with e-mail: "
                                    + clientCreateRequestDto.getEmail()))
                    .andExpect(jsonPath("$.path")
                            .value("/client"));
            verify(clientPortIn, times(1)).createClient(any(ClientCreateRequestDto.class));
        }

        @Test
        void shouldThrowInvalidCpfDocumentNumberExceptionWhenCreateClientWithInvalidCpf() throws Exception {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.getCpf().setDocumentNumber("999.999.999-99");

            when(clientPortIn.createClient(any(ClientCreateRequestDto.class)))
                    .thenThrow(new InvalidCpfDocumentNumberException(
                            "Invalid CPF document number: 99999999999"));

            // Act & Assert
            mockMvc.perform(post("/client")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(clientCreateRequestDto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.timestamp")
                            .isNotEmpty())
                    .andExpect(jsonPath("$.status")
                            .value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.error")
                            .value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                    .andExpect(jsonPath("$.message")
                            .value("Invalid CPF document number: 99999999999"))
                    .andExpect(jsonPath("$.path")
                            .value("/client"));
            verify(clientPortIn, times(1)).createClient(any(ClientCreateRequestDto.class));
        }

        @Test
        void shouldThrowCepAddressNotFoundExceptionWhenCreateClientWithNotFoundCep() throws Exception {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.getAddress().setCep("99999-999");

            when(clientPortIn.createClient(any(ClientCreateRequestDto.class)))
                    .thenThrow(new CepAddressNotFoundException(
                            "CEP not found: 99999999"));

            // Act & Assert
            mockMvc.perform(post("/client")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(clientCreateRequestDto)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp")
                            .isNotEmpty())
                    .andExpect(jsonPath("$.status")
                            .value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.error")
                            .value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .andExpect(jsonPath("$.message")
                            .value("CEP not found: 99999999"))
                    .andExpect(jsonPath("$.path")
                            .value("/client"));
            verify(clientPortIn, times(1)).createClient(any(ClientCreateRequestDto.class));
        }

        @Test
        void shouldThrowMethodArgumentNotValidExceptionWhenCreateClientWithEmptyClientFullName() throws Exception {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.setFullName("");

            // Act & Assert
            mockMvc.perform(post("/client")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(clientCreateRequestDto)))
                    .andExpect(status().isBadRequest());
            verify(clientPortIn, never()).createClient(any(ClientCreateRequestDto.class));
        }
    }

    @Nested
    class GetClient {

        @Test
        void shouldGetClientById() throws Exception {
            // Arrange
            Long clientId = 1L;
            ClientResponseDto clientResponseDto = ClientHelper.createClientResponseDtoObject();

            when(clientPortIn.getClientById(any(Long.class))).thenReturn(clientResponseDto);

            // Act & Assert
            mockMvc.perform(get("/client/{clientId}", clientId))
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(clientResponseDto)));
            verify(clientPortIn, times(1)).getClientById(any(Long.class));
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenClientNotFoundById() throws Exception {
            // Arrange
            Long clientId = 1L;

            when(clientPortIn.getClientById(any(Long.class)))
                    .thenThrow(new ClientNotFoundException("Client not found with id: " + clientId));

            // Act & Assert
            mockMvc.perform(get("/client/{clientId}", clientId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp")
                            .isNotEmpty())
                    .andExpect(jsonPath("$.status")
                            .value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.error")
                            .value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .andExpect(jsonPath("$.message")
                            .value("Client not found with id: "
                                    + clientId))
                    .andExpect(jsonPath("$.path")
                            .value("/client/" + clientId));
            verify(clientPortIn, times(1)).getClientById(any(Long.class));
        }

        @Test
        void shouldGetClientByEmail() throws Exception {
            // Arrange
            String email = "marcos@silva.com";
            ClientResponseDto clientResponseDto = ClientHelper.createClientResponseDtoObject();

            when(clientPortIn.getClientByEmail(any(String.class))).thenReturn(clientResponseDto);

            // Act & Assert
            mockMvc.perform(get("/client?email={email}", email))
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(clientResponseDto)));
            verify(clientPortIn, times(1)).getClientByEmail(any(String.class));
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenClientNotFoundByEmail() throws Exception {
            // Arrange
            String email = "not@exists.com";

            when(clientPortIn.getClientByEmail(any(String.class)))
                    .thenThrow(new ClientNotFoundException("Client not found with e-mail: " + email));

            // Act & Assert
            mockMvc.perform(get("/client?email={email}", email))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp")
                            .isNotEmpty())
                    .andExpect(jsonPath("$.status")
                            .value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.error")
                            .value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .andExpect(jsonPath("$.message")
                            .value("Client not found with e-mail: "
                                    + email))
                    .andExpect(jsonPath("$.path")
                            .value("/client"));
            verify(clientPortIn, times(1)).getClientByEmail(any(String.class));
        }

        @Test
        void shouldGetAllClients() throws Exception {
            // Arrange
            AllClientsResponseDto allClientsResponseDto = ClientHelper.createAllClientsResponseDtoObject();

            when(clientPortIn.getAllClients()).thenReturn(allClientsResponseDto);

            // Act & Assert
            mockMvc.perform(get("/client/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(allClientsResponseDto)));
            verify(clientPortIn, times(1)).getAllClients();
        }
    }

    @Nested
    class UpdateClient {

        @Test
        void shouldUpdateClient() throws Exception {
            // Arrange
            Long clientId = 1L;
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();
            ClientResponseDto clientResponseDto = ClientHelper.createClientResponseDtoObject();
            clientResponseDto.setFullName(clientUpdateRequestDto.getFullName());

            when(clientPortIn.updateClient(any(Long.class), any(ClientUpdateRequestDto.class)))
                    .thenReturn(clientResponseDto);

            // Act & Assert
            mockMvc.perform(put("/client/{id}", clientId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(clientUpdateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(clientResponseDto)));
            verify(clientPortIn, times(1))
                    .updateClient(any(Long.class), any(ClientUpdateRequestDto.class));
        }
    }

    @Nested
    class DeleteClient {

        @Test
        void shouldDeleteClientById() throws Exception {
            // Arrange
            Long clientId = 1L;

            doNothing().when(clientPortIn).deleteClientById(any(Long.class));

            // Act & Assert
            mockMvc.perform(delete("/client/{clientId}", clientId))
                    .andExpect(status().isNoContent());
            verify(clientPortIn, times(1)).deleteClientById(any(Long.class));
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenDeleteClientByIdWithInexistentId() throws Exception {
            // Arrange
            Long clientId = 1L;

            doThrow(new ClientNotFoundException("Client not found with id: " + clientId))
                    .when(clientPortIn).deleteClientById(any(Long.class));

            // Act & Assert
            mockMvc.perform(delete("/client/{clientId}", clientId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp")
                            .isNotEmpty())
                    .andExpect(jsonPath("$.status")
                            .value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.error")
                            .value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .andExpect(jsonPath("$.message")
                            .value("Client not found with id: "
                                    + clientId))
                    .andExpect(jsonPath("$.path")
                            .value("/client/" + clientId));
            verify(clientPortIn, times(1)).deleteClientById(any(Long.class));
        }

    }

    private static String asJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
