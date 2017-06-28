package com.worldrunner.model.Authentication;

import com.worldrunner.model.User;

/**
 * Created by vuta on 27/06/2017.
 */
public class AuthorizationResponse {
    public Authentication authentication;
    public User user;

    public AuthorizationResponse() {
    }

    public AuthorizationResponse(Authentication authentication, User user) {
        this.authentication = authentication;
        this.user = user;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
