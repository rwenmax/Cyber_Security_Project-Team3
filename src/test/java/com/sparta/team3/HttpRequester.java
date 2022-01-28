package com.sparta.team3;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequester {

    public static HttpResponse<String> getResponse(HttpRequest request) {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    public static HttpResponse<String> getRequest(String url, String json) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .method("GET",HttpRequest.BodyPublishers.ofString(json))
                .build();
        return getResponse(request);
    }
    public static HttpResponse<String> postRequest(String url, String json) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return getResponse(request);
    }
    public static HttpResponse<String> deleteRequest(String url, String json) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .method("DELETE",HttpRequest.BodyPublishers.ofString(json))
                .build();
        return getResponse(request);
    }
    public static HttpResponse<String> putRequest(String url, String json) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return getResponse(request);
    }
}
