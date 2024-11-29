package br.com.fiap.client_management_ms.bdd.steps;

import br.com.fiap.client_management_ms.bdd.service.GetClientService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class GetClientSteps {

    private final GetClientService getClientService = new GetClientService();

    @Given("that I have the client id {long}")
    public void thatIHaveTheClientId(Long clientId) {
        getClientService.setClientId(clientId);
    }

    @When("I send the get request to the get client endpoint {string}")
    public void iSendTheGetRequestToTheGetClientEndpoint(String endPoint) {
        getClientService.sendGetRequest(endPoint);
    }

    @Then("the response status code from the get request should be {int}")
    public void theResponseStatusCodeFromTheGetRequestShouldBe(int statusCode) {
        assertThat(getClientService.getResponse().getStatusCode()).isEqualTo(statusCode);
    }
}
