package com.sparta.team3.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.http.HttpResponse;

import static com.sparta.team3.HttpRequester.*;


public class UserProfileControllerTest {

    private static final String ROOT_URL = "http://localhost:8080/cyberteam3/user";

    @BeforeAll
    public static void getConnections() {
        //putUserByTokenResponse = getRequest(PUT_USER_BY_TOKEN);
    }

    @ParameterizedTest
    @DisplayName("Get user by token")
    @CsvSource({"abcdefg"})
    public void getGetUserByTokenStatus(String token) {
        HttpResponse<String> response = getRequest(ROOT_URL  + "s/" + token, "");
        Assertions.assertEquals(200, response.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Get user by token")
    @CsvSource({"kamil, abcdefg"})
    public void getGetUserByTokenStatus(String user, String token) {
        HttpResponse<String> response = getRequest(ROOT_URL  + "/" + user + "/" + token, "");
        Assertions.assertEquals(200, response.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Adding User")
    @CsvSource({"Test,Test"})
    public void getPostUserStatus(String profileUsername) {
        HttpResponse<String> response = postRequest(ROOT_URL + "/add",
                " { \"profileUsername\": \"" + profileUsername + "\" , \"profilePassword\": \"" + profileUsername +"\"} "
        );
        Assertions.assertEquals(200, response.statusCode());
    }
    @ParameterizedTest
    @DisplayName("Deleting User")
    @CsvSource({"abcdefg, ishmael"})
    public void getDeleteUserStatus(String token, String userName) {
        HttpResponse<String> response = deleteRequest(ROOT_URL  + "/delete",
                " { \"token\": \"" + token + "\" , \"userName\": \"" + userName +"\"}"
        );
        Assertions.assertEquals(200, response.statusCode());
    }

}