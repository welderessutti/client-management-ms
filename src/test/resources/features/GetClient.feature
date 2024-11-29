Feature: Get Client
  As a API user
  I would like to get a client
  and the response is correctly returned

  Scenario: Well succeed get client
    Given that I have the client id 1
    When I send the get request to the get client endpoint "/api/client/{id}"
    Then the response status code from the get request should be 200

  Scenario: Bad succeed get client with an non-existent client id
    Given that I have the client id 999
    When I send the get request to the get client endpoint "/api/client/{id}"
    Then the response status code from the get request should be 404