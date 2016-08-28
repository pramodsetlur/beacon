
/**
 * Copyright (C) 1999 - 2014 Ticketmaster
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package hackathon.slashhack.bdd;

import java.util.HashMap;
import java.util.Map;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TestSteps {
    private String requestBody;
    private Response rs;

    @Given("^I have the body:$")
    public void I_have_the_body(String body) throws Throwable {
        requestBody = body;
    }

    @Then("^I expect to see the response:$")
    public void I_expect_to_see_the_response(String response) throws Throwable {
        TestCase.assertEquals(normalize(response), normalize(rs.getBody().print()));
    }

    @When("^I POST the body to \"([^\"]*)\"$")
    public void I_POST_the_body_to(String url) throws Throwable {
        rs = RestAssured.post(url, requestBody);
    }

    @When("^I DELETE the URL to \"([^\"]*)\"$")
    public void I_DELETE_the_URL_to(String arg1) throws Throwable {
        rs = RestAssured.delete(arg1);
    }

    @When("^I PUT the URL to \"([^\"]*)\"$")
    public void I_PUT_the_URL_to(String arg1) throws Throwable {
        rs = RestAssured.put(arg1);

    }

    @When("^I GET the URL to \"([^\"]*)\"$")
    public void I_GET_the_URL_to(String url) throws Throwable {
        rs = RestAssured.get(url);
    }

    @When("^I GET the URL to \"([^\"]*)\" with parameters \"(.*)\"$")
    public void I_GET_the_URL_to_with_params(String url, String parameters)
            throws Throwable {
        Map<String, String> paramsMap = generateParametersMap(parameters);
        rs = RestAssured.given().params(paramsMap).get(url);
    }

    @Then("^I expect to see \"([^\"]*)\" content$")
    public void I_expect_to_see_the_content_type(String contentType) throws Throwable {
        assertTrue(contentType + " content was expected but got " + rs.getContentType(),
                rs.getContentType().contains(contentType));
    }

    @Then("^I expect to see the response code \"(\\d+)\"$")
    public void I_expect_to_see_the_response_code(int rc) throws Throwable {
        assertEquals(rc, rs.getStatusCode());
    }

    private String normalize(String body) {
        return body.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\\t+<", "<").replaceAll("\\s+<", "<");
    }

    private Map<String, String> generateParametersMap(String parameters) {
        String[] paramsArray = parameters.split(" ");
        assertTrue(
                "The parameters were not in the correct format. Usage:<key1=value1 key2=value2 key3=value3 ...>",
                paramsArray.length > 0);
        Map<String, String> paramsMap = new HashMap<String, String>();
        for (String keyValuePair : paramsArray) {
            String[] requestParameter = keyValuePair.split("=");
            paramsMap.put(requestParameter[0], requestParameter[1]);
        }
        return paramsMap;
    }
}
