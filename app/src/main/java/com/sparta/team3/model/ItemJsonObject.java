package com.sparta.team3.model;

import java.util.Arrays;

public class ItemJsonObject
{
    String token;
    String item;
    String type;

    public ItemJsonObject(String token, String item, String type)
    {
        this.token = token;
        this.item = item;
        this.type = type;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "ItemJsonObject{" +
                "token='" + token + '\'' +
                ", item='" + item + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

