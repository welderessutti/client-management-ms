Feature: Create Client
  As a API user
  I would like to create a new client
  and the registration is saved correctly in the system

  Scenario: Well succeed create client
    Given that I have the following client data:
      | field              | value              |
      | fullName           | Client Example     |
      | email              | client@example.com |
      | mobilePhoneNumber  | 99-999999999       |
      | cpf.documentNumber | 527.473.860-58     |
      | address.cep        | 01001-000          |
      | address.number     | 99                 |
    When I send the post request to the create client endpoint "/api/client"
    Then the response status code from the post request should be 201
    And the "id" response field is not null

  Scenario: Bad succeed create client when cpf.documentNumber is invalid
    Given that I have the following client data:
      | field              | value              |
      | fullName           | Client Example     |
      | email              | client@example.com |
      | mobilePhoneNumber  | 99-999999999       |
      | cpf.documentNumber | 999.999.999-99     |
      | address.cep        | 01001-000          |
      | address.number     | 99                 |
    When I send the post request to the create client endpoint "/api/client"
    Then the response status code from the post request should be 400