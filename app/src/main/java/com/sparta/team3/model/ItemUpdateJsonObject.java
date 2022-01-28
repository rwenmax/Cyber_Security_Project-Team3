package com.sparta.team3.model;

public class ItemUpdateJsonObject
{
    String token;
    String target;
    String value;

    public ItemUpdateJsonObject(String token, String target, String value)
    {
        this.token = token;
        this.target = target;
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public String getTarget() {
        return target;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ItemUpdateJsonObject{" +
                "token='" + token + '\'' +
                ", target='" + target + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
