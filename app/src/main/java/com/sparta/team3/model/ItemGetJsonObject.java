package com.sparta.team3.model;

public class ItemGetJsonObject
{
    String token;

    public ItemGetJsonObject(String token)
    {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public ItemGetJsonObject()
    {}

    @Override
    public String toString() {
        return "ItemGetJsonObject{" +
                "token='" + token + '\'' +
                '}';
    }
}
