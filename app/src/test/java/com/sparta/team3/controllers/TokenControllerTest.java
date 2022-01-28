package com.sparta.team3.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.http.HttpResponse;

import static com.sparta.team3.HttpRequester.*;


public class TokenControllerTest {

    private static final String ROOT_URL = "http://localhost:8080/cyberteam3/token";

    @ParameterizedTest
    @DisplayName("Add token by user ID")
    @ValueSource(ints = {3})
    public void getPostTokenByUserIDStatus(int userID) {
        HttpResponse<String> response = postRequest(ROOT_URL + "/add/" + userID, "");
        Assertions.assertEquals(200, response.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Get tokens by name")
    @CsvSource({"abcdefg"})
    public void getGetTokensStatus(String token) {
        HttpResponse<String> response = getRequest(ROOT_URL + "s/" + token, "");
        Assertions.assertEquals(200, response.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Delete token by id")
    @CsvSource({"2, abcdefg"})
    public void getDeleteTokenByIDStatus(String id, String tokenA) {
        HttpResponse<String> response = deleteRequest(ROOT_URL + "/deletebyid/" + id + "/" + tokenA, "");
        Assertions.assertEquals(200, response.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Delete token by token")
    @CsvSource({"nginx, abcdefg"})
    public void getDeleteTokenByTokenStatus(String token, String tokenA) {
        HttpResponse<String> response = deleteRequest(ROOT_URL + "/deletebytoken/" + token + "/" + tokenA, "");
        Assertions.assertEquals(200, response.statusCode());
    }
}