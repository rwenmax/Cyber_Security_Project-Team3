package com.sparta.team3.controllers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.http.HttpResponse;

import static com.sparta.team3.HttpRequester.*;

public class ProfileItemControllerTest {

    private static final String ROOT_URL = "http://localhost:8080/cyberteam3/item";

    @ParameterizedTest
    @DisplayName("Add item")
    @CsvSource({"abcdefg, tree, package"})
    public void getAddItemStatus(String token,String item, String type) {
        HttpResponse<String> response = postRequest(ROOT_URL + "/add",
                " { \"token\": \"" + token + "\", \"item\": \"" + item + "\", \"type\": \"" + type + "\"}"
        );
        Assertions.assertEquals(200, response.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Update item")
    @CsvSource({"abcdefg, tree, docker"})
    public void getUpdateItemStatus(String token, String target, String value) {
        HttpResponse<String> response = putRequest(ROOT_URL + "/update",
                " { \"token\": \"" + token + "\", \"target\": \"" + target + "\", \"value\": \"" + value + "\"}"
        );
        Assertions.assertEquals(200, response.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Get all items")
    @CsvSource({"abcdefg"})
    public void getAllItemsStatus(String token) {
        HttpResponse<String> response = getRequest(ROOT_URL + "/get",
                " { \"token\": \"" + token + "\"}"
        );
        Assertions.assertEquals(200, response.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Delete item/s")
    @CsvSource({"token, tree"})
    public void getDeleteItemsStatus(String token, String name) {
        HttpResponse<String> response = deleteRequest(ROOT_URL + "/delete",
                " { \"token\": \"" + token + "\" , \"name\": \"" + name +"\"}"
        );
        Assertions.assertEquals(200, response.statusCode());
    }

    @ParameterizedTest
    @DisplayName("Delete all items")
    @CsvSource({"abcdefg"})
    public void getDeleteAllItemsStatus(String token) {
        HttpResponse<String> response = deleteRequest(ROOT_URL + "s/delete",
                " { \"token\": \"" + token + "\"}"
        );
        Assertions.assertEquals(200, response.statusCode());
    }
}
