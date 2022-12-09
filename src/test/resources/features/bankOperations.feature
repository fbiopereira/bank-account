Feature: Testing banking operations - success

  Scenario: Bank receives a POST request at reset endpoint
      When I receive a POST request on reset endpoint
      And the bank responds 200 as status code
      Then Should have response body
        """
        OK
        """


  Scenario: Bank receives a POST request at event endpoint
    Given json body
    """
      {
        "type":"deposit",
        "destination":"100",
        "amount":10}
    """
    When I receive a POST request on event endpoint
    And the bank responds 201 as status code
    Then Should have response body
        """
        {"destination":{"id":"100","balance":10}}
        """