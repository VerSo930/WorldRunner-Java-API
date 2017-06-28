package com.worldrunner.model.Authentication;

import com.worldrunner.model.User;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */
public class Authentication {

    private String token;
    private User user;

    public Authentication() {
    }

    public Authentication( String token, User user) {

        this.token = token;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
