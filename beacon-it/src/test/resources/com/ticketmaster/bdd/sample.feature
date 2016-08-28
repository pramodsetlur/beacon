Feature: Service Template Endpoints Features

  @sample
  Scenario: When I request the /gitInfo endpoint I receive an OK response
    When I GET the URL to "/gitInfo"
    Then I expect to see the response code "200"

  @sample
  Scenario: When I request the /metrics/values endpoint that TMPS-Metrics Library generated I receive an OK response
    When I GET the URL to "/metrics/values"
    Then I expect to see the response code "200"

  @sample
  Scenario: When I request the /metrics/registry endpoint that TMPS-Metrics Library generated I receive an OK response
    When I GET the URL to "/metrics/registry"
    Then I expect to see the response code "200"

