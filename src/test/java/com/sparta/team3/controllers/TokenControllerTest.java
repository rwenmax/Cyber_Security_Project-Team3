package com.sparta.team3.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.context.jdbc.Sql;

import java.net.http.HttpResponse;

import static com.sparta.team3.HttpRequester.*;


public class UserProfileControllerTest {

    private static final String ROOT_URL = "http://localhost:8081/cyberteam3/user";

    @ParameterizedTest
    @DisplayName("Get user by token")
    @CsvSource({"abcdefg"})
    public void getGetUserByTokenStatus(String token) {
        HttpResponse<String> getUserByTokenResponse = getRequest(ROOT_URL  + "s/" + token);
        Assertions.assertEquals(200, getUserByTokenResponse.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Get user by token")
    @CsvSource({"kamil, abcdefg"})
    public void getGetUserByTokenStatus(String user, String token) {
        HttpResponse<String> getUserByNameAndTokenResponse = getRequest(ROOT_URL  + "/" + user + "/" + token);
        Assertions.assertEquals(200, getUserByNameAndTokenResponse.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Adding User")
    @CsvSource({"Test,Test"})
    public void getPostUserStatus(String profileUsername) {
        HttpResponse<String> postUserResponse = postRequest(ROOT_URL + "/add",
                " { \"profileUsername\": \"" + profileUsername + "\" , \"profilePassword\": \"" + profileUsername +"\"} "
        );
        Assertions.assertEquals(200, postUserResponse.statusCode());
    }
    @ParameterizedTest
    @DisplayName("Deleting User")
    @CsvSource({"abcdefg, kamil"})
    public void getDeleteUserStatus(String token, String userName) {
        HttpResponse<String> deleteUserResponse = deleteRequest(ROOT_URL  + "/delete",
                " { \"token\": \"" + token + "\" , \"userName\": \"" + userName +"\"}"
        );
        Assertions.assertEquals(200, deleteUserResponse.statusCode());
    }

}