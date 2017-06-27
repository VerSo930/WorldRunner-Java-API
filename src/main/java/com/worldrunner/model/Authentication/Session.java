package com.worldrunner.model.Authentication;

import com.worldrunner.model.User;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */
public class Session {

    private int userId;
    private String token;

    public Session() {
    }

    public Session(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
