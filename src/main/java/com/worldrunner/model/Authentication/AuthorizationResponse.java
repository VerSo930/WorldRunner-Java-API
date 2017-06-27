package com.worldrunner.model.Authentication;

import com.worldrunner.model.User;

/**
 * Created by vuta on 27/06/2017.
 */
public class AuthorizationResponse {
    public Session session;
    public User user;

    public AuthorizationResponse() {
    }

    public AuthorizationResponse(Session session, User user) {
        this.session = session;
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
