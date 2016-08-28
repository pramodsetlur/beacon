Feature: Service Template Endpoints For Baseline/Boilerplate features

@boilerplate
Scenario: When I request the /gitInfo endpoint I receive an OK response
  When I GET the URL to "/gitInfo"
  Then I expect to see the response code "200"
    And I expect to see "json" content

@boilerplate
Scenario: When I request the /metrics/values endpoint that TMPS-Metrics Library generated I receive an OK response
  When I GET the URL to "/metrics/values"
  Then I expect to see the response code "200"
    And I expect to see "json" content

@boilerplate
Scenario: When I request the /metrics/registry endpoint that TMPS-Metrics Library generated I receive an OK response
  When I GET the URL to "/metrics/registry"
  Then I expect to see the response code "200"
    And I expect to see "json" content

@boilerplate
Scenario: When I request the /health/healthcheck endpoint that the Service Foundation library created I receive an OK response
  When I GET the URL to "/health/healthcheck"
  Then I expect to see the response code "200"
    And I expect to see "json" content

@boilerplate
Scenario: When I request the /health/heartbeat endpoint that the Service Foundation library created I receive an OK response
  Given I DELETE the URL to "/health/heartbeat/kill"
  When I GET the URL to "/health/heartbeat"
  Then I expect to see the response code "200"
    And I expect to see "json" content

@boilerplate
Scenario: When I PUT to the /health/heartbeat/kill endpoint that the Service Foundation library created will return a 599 response
  Given I DELETE the URL to "/health/heartbeat/kill"
  When I PUT the URL to "/health/heartbeat/kill"
      And I GET the URL to "/health/heartbeat"
  Then I expect to see the response code "599"
    And I expect to see "json" content


@boilerplate
Scenario: When I PUT to the /health/heartbeat/kill then DELETE from the same place, I will get a 200  back
  Given I DELETE the URL to "/health/heartbeat/kill"
  When I PUT the URL to "/health/heartbeat/kill"
      And I DELETE the URL to "/health/heartbeat/kill"
      And I GET the URL to "/health/heartbeat"
  Then I expect to see the response code "200"
    And I expect to see "json" content

@boilerplate
Scenario: When I request the /health/catalog endpoint I receive an OK response with json format.
  When I GET the URL to "/health/catalog"
  Then I expect to see the response code "200"
  And I expect to see "json" content

@boilerplate
Scenario: When I request the /health/catalog.json endpoint I receive an OK response with json format
  When I GET the URL to "/health/catalog.json"
  Then I expect to see the response code "200"
  And I expect to see "json" content
