Feature: Testing banking operations - success

  Scenario: Bank receives a POST request at reset endpoint
      When I receive a POST request on reset endpoint
      And the bank responds 200 as status code
      Then Should have text response body
        """
        OK
        """

  Scenario: Bank receives a GET request at balance endpoint - account found
    Given I create an account with "100" and amount 20
    When I receive a GET request on balance endpoint with accountId "100"
    And the bank responds 200 as status code
    Then Should have text response body
        """
          20
        """

  Scenario: Bank receives a GET request at balance endpoint - account not found
    When I receive a GET request on balance endpoint with accountId "1234"
    And the bank responds 404 as status code
    Then Should have text response body
        """
          0
        """

  Scenario: Bank receives a POST request at event endpoint with deposit operation - new account
    Given json body
    """
      {
        "type":"deposit",
        "destination":"100",
        "amount":10
      }
    """
    When I receive a POST request on event endpoint
    And the bank responds 201 as status code
    Then Should have json response body
        """
        {
          "destination":
            {
              "id":"100",
              "balance":10
            }
        }
        """

  Scenario: Bank receives a POST request at event endpoint with deposit operation - existing account
    Given I create an account with "100" and amount 10
    Given json body
    """
      {
        "type":"deposit",
        "destination":"100",
        "amount":10
      }
    """
    When I receive a POST request on event endpoint
    Then the bank responds 201 as status code
    Then Should have json response body
        """
        {
          "destination":
            {
              "id":"100",
              "balance":20
            }
        }
        """

  Scenario: Bank receives a POST request at event endpoint with withdraw operation - account not found
    Given json body
    """
      {
        "type":"withdraw",
        "origin":"200",
        "amount":10
      }
    """
    When I receive a POST request on event endpoint
    Then the bank responds 404 as status code
    Then Should have text response body
        """
        0
        """

  Scenario: Bank receives a POST request at event endpoint with withdraw operation - existing account
    Given I create an account with "100" and amount 20
    Given json body
    """
      {
        "type":"withdraw",
        "origin":"100",
        "amount":5
      }
    """
    When I receive a POST request on event endpoint
    Then the bank responds 201 as status code
    Then Should have json response body
        """
        {
          "origin":
            {
              "id":"100",
              "balance":15
            }
        }
        """

  Scenario: Bank receives a POST request at event endpoint with transfer operation - existing account
    Given I create an account with "100" and amount 15
    Given json body
    """
      {
        "type":"transfer",
        "origin":"100",
        "amount":15,
        "destination":"300"
      }
    """
    When I receive a POST request on event endpoint
    Then the bank responds 201 as status code
    Then Should have json response body
        """
        {
          "origin":
            {
              "id":"100",
              "balance":0
            },
          "destination":
            {
              "id":"300",
              "balance":15
             }
        }
        """

  Scenario: Bank receives a POST request at event endpoint with transfer operation - account not found
    Given json body
    """
      {
        "type":"transfer",
        "origin":"200",
        "amount":15,
        "destination":"300"
      }

    """
    When I receive a POST request on event endpoint
    Then the bank responds 404 as status code
    Then Should have text response body
        """
          0
        """