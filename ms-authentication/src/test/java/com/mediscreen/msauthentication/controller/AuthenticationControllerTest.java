package com.mediscreen.msauthentication.controller;

import com.mediscreen.msauthentication.JWTTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
@ComponentScan({"com.mediscreen.msauthentication.configuration", "com.mediscreen.msauthentication.controller"})
class AuthenticationControllerTest {
    private String token = JWTTest.token;
    private String wrongToken = JWTTest.wrongToken;

    @Autowired
    MockMvc mockMvc;

    @Tag("AuthenticationControllerTest")
    @Test
    void GET_validateTokenTest() throws Exception {
        String url = "/validate-token";
        mockMvc.perform(get(url))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get(url + "?token=token"))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get(url + "?token=" + wrongToken))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get(url + "?token=" + token))
                .andExpect(status().is2xxSuccessful());
    }

    @Tag("AuthenticationControllerTest")
    @Test
    void POST_generateTokenTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/generate-token")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"username\", \"password\": \"password\", \"rememberUser\": true}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }

    @Tag("AuthenticationControllerTest")
    @Test
    void POST_generateTokenTest_emptyBody() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/generate-token")
                .accept(MediaType.APPLICATION_JSON).content("{}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }
}