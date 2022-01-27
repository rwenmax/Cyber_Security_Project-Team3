package com.sparta.team3.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static com.sparta.team3.HttpRequester.*;


public class UserProfileControllerTest {

    private static final String POST_USER = "http://localhost:8081/user/new";
    private static final String POST_TOKEN = "http://localhost:8081/user/delete";
    private static final String DELETE_USER = "http://localhost:8081/user/delete";

    private static HttpResponse<String> postUserResponse = null;
    private static HttpResponse<String> deleteUserResponse = null;

    @BeforeAll
    public static void getConnections() {
        postUserResponse = postRequest(POST_USER,
                """
                        {
                            "profileUsername": "Test",
                            "profilePassword": "Test"
                        }
                        """
        );
        postUserResponse = postRequest(DELETE_USER,
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
    public void getPostUserStatus() {
        Assertions.assertEquals(200, postUserResponse.statusCode());
    }
    @Test
    @DisplayName("1 Given all playlist request, return 200 status")
    public void getDeleteUserStatus() {
        Assertions.assertEquals(200, deleteUserResponse.statusCode());
    }

}