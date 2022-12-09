package com.fbiopereira.bankaccount.bdd.steps;

import com.fasterxml.jackson.core.JsonParser;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

public class RestSteps {

    @Autowired
    private WebApplicationContext webApplicationContext;

    public static MockMvc contextMockMvc;
    public static String contextJson = "";
    public static ResultActions contextResultActions;


    @Before
    public void setUp() {
        contextMockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

    }

    @When("I receive a POST request on reset endpoint")
    public void request_to_reset() throws Exception {
        contextResultActions = contextMockMvc.perform(MockMvcRequestBuilders
                .request("POST",
                        URI.create("/api/bank/reset"))
                .content(contextJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @When("I receive a POST request on event endpoint")
    public void request_to_event() throws Exception {
        contextResultActions = contextMockMvc.perform(MockMvcRequestBuilders
                .request("POST",
                        URI.create("/api/bank/event"))
                .content(contextJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Given("json body")
    public void json_body(String body) {
        contextJson = body;
    }

    @Then("the bank responds {int} as status code")
    public void should_return_status_code_and_method(int statusCode) throws Exception {
        contextResultActions.andExpect(MockMvcResultMatchers.status().is(statusCode));
    }

    @Then("Should have response body")
    public void should_have_response_body(String body) throws Exception {
        String expected = body.trim();
        String received = contextResultActions.andReturn().getResponse().getContentAsString().trim();

        Assertions.assertThat(expected).isEqualTo(received);
    }


}
