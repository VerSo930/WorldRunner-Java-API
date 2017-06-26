package com.worldrunner.model.Authentication;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */
public class Session {

    private int id;
    private int userId;
    private String session;
    private String lastActivity;

    public Session() {
    }

    public String getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity = lastActivity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
