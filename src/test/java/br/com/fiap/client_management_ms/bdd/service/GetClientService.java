package br.com.fiap.client_management_ms.bdd.service;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetClientService {

    private Long clientId;
    private final String baseUrl = "http://localhost:8080";
    private Response response;

    public void sendGetRequest(String endPoint) {
        String url = baseUrl + endPoint;
        response = given()
                .when()
                .get(url, clientId);
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Response getResponse() {
        return response;
    }
}
