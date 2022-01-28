package com.sparta.team3.model;

public class ItemDeleteJsonObject
{
    String token;
    String name;

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ItemDeleteJsonObject{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public ItemDeleteJsonObject(){}
}
