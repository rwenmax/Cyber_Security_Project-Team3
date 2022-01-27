package com.sparta.team3.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static com.sparta.team3.HttpRequester.*;


public class LoginControllerTest {

    private static final String POST_LOGIN = "http://localhost:8081/login/new";

    private static HttpResponse<String> postLoginResponse = null;

    @BeforeAll
    public static void getConnections() {
        postLoginResponse = postRequest(POST_LOGIN,
                """
                        {
                            "profileUsername": "Test",
                            "profilePassword": "Test"
                        }
                        """
        );
    }

    @Test
    @DisplayName("1 Given all playlist request, return 200 status")
    public void getPostLoginStatus() {
        Assertions.assertEquals(200, postLoginResponse.statusCode());
    }

}