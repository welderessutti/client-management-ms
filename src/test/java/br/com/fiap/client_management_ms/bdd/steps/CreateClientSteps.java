package br.com.fiap.client_management_ms.bdd.steps;

import br.com.fiap.client_management_ms.bdd.service.CreateClientService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateClientSteps {

    private final CreateClientService createClientService = new CreateClientService();

    @Given("that I have the following client data:")
    public void thatIHaveTheFollowingClientData(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            createClientService.setFieldsClient(columns.get("field"), columns.get("value"));
        }
    }

    @When("I send the post request to the create client endpoint {string}")
    public void iSendThePostRequestToTheCreateClientEndpoint(String endPoint) {
        createClientService.sendPostRequest(endPoint);
    }

    @Then("the response status code from the post request should be {int}")
    public void theResponseStatusCodeFromThePostRequestShouldBe(int statusCode) {
        assertThat(createClientService.getResponse().getStatusCode()).isEqualTo(statusCode);
    }

    @And("the {string} response field is not null")
    public void theResponseFieldIsNotNull(String field) {
        assertThat(createClientService.getResponse().jsonPath().getString(field)).isNotNull();
    }
}
