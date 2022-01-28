package com.sparta.team3.model;

public class UserDeleteJsonObject
{
    String token;
    String userName;

    public UserDeleteJsonObject(String token, String userName)
    {
        this.token = token;
        this.userName = userName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "UserDeleteContainer{" +
                "token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
