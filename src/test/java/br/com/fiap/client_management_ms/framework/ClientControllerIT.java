package br.com.fiap.client_management_ms.framework;

import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.utils.ClientHelper;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Transactional
public class ClientControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class CreateClient {

        @Test
        void shouldCreateClient() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clientCreateRequestDto)
                    // Act
                    .when()
                    .post("/api/client")
                    // Assert
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("id", notNullValue());
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingCpf() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.getCpf().setDocumentNumber("32059291011");
            clientCreateRequestDto.setEmail("existing@cpf.com");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clientCreateRequestDto)
                    // Act
                    .when()
                    .post("/api/client")
                    // Assert
                    .then()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .body(matchesJsonSchemaInClasspath("schemas/error-response.json"))
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(HttpStatus.CONFLICT.value()))
                    .body("error", equalTo(HttpStatus.CONFLICT.getReasonPhrase()))
                    .body("message", equalTo("Client already exists with CPF: "
                            + clientCreateRequestDto.getCpf().getDocumentNumber()))
                    .body("path", equalTo("/api/client"));
        }

        @Test
        void shouldThrowClientAlreadyExistsExceptionWhenCreateClientWithExistingEmail() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.getCpf().setDocumentNumber("87293807074");
            clientCreateRequestDto.setEmail("leticia@martins.com.br");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clientCreateRequestDto)
                    // Act
                    .when()
                    .post("/api/client")
                    // Assert
                    .then()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .body(matchesJsonSchemaInClasspath("schemas/error-response.json"))
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(HttpStatus.CONFLICT.value()))
                    .body("error", equalTo(HttpStatus.CONFLICT.getReasonPhrase()))
                    .body("message", equalTo("Client already exists with e-mail: "
                            + clientCreateRequestDto.getEmail()))
                    .body("path", equalTo("/api/client"));
        }

        @Test
        void shouldThrowInvalidCpfDocumentNumberExceptionWhenCreateClientWithInvalidCpf() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.getCpf().setDocumentNumber("999.999.999-99");
            clientCreateRequestDto.setEmail("another2@email.com");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clientCreateRequestDto)
                    // Act
                    .when()
                    .post("/api/client")
                    // Assert
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(matchesJsonSchemaInClasspath("schemas/error-response.json"))
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(HttpStatus.BAD_REQUEST.value()))
                    .body("error", equalTo(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                    .body("message", equalTo("Invalid CPF document number: 99999999999"))
                    .body("path", equalTo("/api/client"));
        }

        @Test
        void shouldThrowCepAddressNotFoundExceptionWhenCreateClientWithNotFoundCep() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.setEmail("cep@notfound.com.br");
            clientCreateRequestDto.getCpf().setDocumentNumber("314.400.230-50");
            clientCreateRequestDto.getAddress().setCep("99999-999");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clientCreateRequestDto)
                    // Act
                    .when()
                    .post("/api/client")
                    // Assert
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(matchesJsonSchemaInClasspath("schemas/error-response.json"))
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(HttpStatus.NOT_FOUND.value()))
                    .body("error", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .body("message", equalTo("CEP not found: 99999999"))
                    .body("path", equalTo("/api/client"));
        }

        @Test
        void shouldThrowMethodArgumentNotValidExceptionWhenCreateClientWithEmptyClientFullName() {
            // Arrange
            ClientCreateRequestDto clientCreateRequestDto = ClientHelper.createClientRequestDtoObject();
            clientCreateRequestDto.setFullName("");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clientCreateRequestDto)
                    // Act
                    .when()
                    .post("/api/client")
                    // Assert
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class GetClient {

        @Test
        void shouldGetClientById() {
            // Arrange
            Long clientId = 2L;

            // Act
            when()
                    .get("/api/client/{clientId}", clientId)
                    // Assert
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/client-response.json"))
                    .body("id", hasToString(clientId.toString()));
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenClientNotFoundById() {
            // Arrange
            Long clientId = 10L;

            // Act
            when()
                    .get("/api/client/{clientId}", clientId)
                    // Assert
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(matchesJsonSchemaInClasspath("schemas/error-response.json"))
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(HttpStatus.NOT_FOUND.value()))
                    .body("error", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .body("message", equalTo("Client not found with id: " + clientId))
                    .body("path", equalTo("/api/client/" + clientId));
        }

        @Test
        void shouldGetClientByEmail() {
            // Arrange
            String email = "fernanda@costa.com.br";

            // Act
            when()
                    .get("/api/client?email={email}", email)
                    // Assert
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/client-response.json"))
                    .body("email", hasToString(email));

        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenClientNotFoundByEmail() {
            // Arrange
            String email = "not@exists.com.br";

            // Act
            when()
                    .get("/api/client?email={email}", email)
                    // Assert
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(matchesJsonSchemaInClasspath("schemas/error-response.json"))
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(HttpStatus.NOT_FOUND.value()))
                    .body("error", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .body("message", equalTo("Client not found with e-mail: " + email))
                    .body("path", equalTo("/api/client"));
        }

        @Test
        void shouldGetAllClients() {
            // Act
            when()
                    .get("/api/client/all")
                    // Assert
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/all-clients-list-response.json"))
                    .body("clients", notNullValue());
        }
    }

    @Nested
    class UpdateClient {

        @Test
        void shouldUpdateClient() {
            // Arrange
            Long clientId = 1L;
            ClientUpdateRequestDto clientUpdateRequestDto = ClientHelper.createClientUpdateRequestDtoObject();

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clientUpdateRequestDto)
                    // Act
                    .when()
                    .put("/api/client/{id}", clientId)
                    // Assert
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/client-response.json"))
                    .body("id", hasToString(clientId.toString()))
                    .body("fullName", equalTo(clientUpdateRequestDto.getFullName()));
        }
    }

    @Nested
    class DeleteClient {

        @Test
        void shouldDeleteClientById() {
            // Arrange
            Long clientId = 3L;

            // Act
            when()
                    .delete("/api/client/{clientId}", clientId)
                    // Assert
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void shouldThrowClientNotFoundExceptionWhenDeleteClientByIdWithInexistentId() {
            // Arrange
            Long clientId = 10L;

            // Act
            when()
                    .delete("/api/client/{clientId}", clientId)
                    // Assert
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(matchesJsonSchemaInClasspath("schemas/error-response.json"))
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(HttpStatus.NOT_FOUND.value()))
                    .body("error", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .body("message", equalTo("Client not found with id: " + clientId))
                    .body("path", equalTo("/api/client/" + clientId));
        }
    }
}
