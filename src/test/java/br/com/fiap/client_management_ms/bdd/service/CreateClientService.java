package br.com.fiap.client_management_ms.bdd.service;

import br.com.fiap.client_management_ms.framework.dto.request.create.AddressCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.create.CpfCreateRequestDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateClientService {

    private final ClientCreateRequestDto clientCreateRequestDto = new ClientCreateRequestDto();
    private final String baseUrl = "http://localhost:8080";
    private Response response;

    public CreateClientService() {
        this.clientCreateRequestDto.setCpf(new CpfCreateRequestDto());
        this.clientCreateRequestDto.setAddress(new AddressCreateRequestDto());
    }

    public void setFieldsClient(String field, String value) {
        switch (field) {
            case "fullName" -> clientCreateRequestDto.setFullName(value);
            case "email" -> clientCreateRequestDto.setEmail(value);
            case "mobilePhoneNumber" -> clientCreateRequestDto.setMobilePhoneNumber(value);
            case "cpf.documentNumber" -> clientCreateRequestDto.getCpf().setDocumentNumber(value);
            case "address.cep" -> clientCreateRequestDto.getAddress().setCep(value);
            case "address.number" -> clientCreateRequestDto.getAddress().setNumber(value);
            default -> throw new IllegalStateException("Unexpected field: " + field);
        }
    }

    public void sendPostRequest(String endPoint) {
        String url = baseUrl + endPoint;
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(clientCreateRequestDto)
                .when()
                .post(url);
    }

    public Response getResponse() {
        return response;
    }
}
