package com.worldrunner.dao;

import com.google.gson.Gson;
import com.worldrunner.Cnst;
import com.worldrunner.model.Authentication.Session;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Database;
import com.worldrunner.tools.Helper;

import java.sql.*;
import java.util.Map;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */
public class AuthenticationDaoImpl implements AuthenticationDao {
    private static Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private User GUser;
    private Helper helper;

    public AuthenticationDaoImpl() {
        //GSession = new Session();
        helper = new Helper();
    }

    @Override
    public User authenticate(final User user) throws CustomException {
return  null;
    }

    @Override
    public Session register(User user) {
        return null;
    }

    @Override
    public Session getSession(User user) {
        return null;
    }

    @Override
    public boolean checkSession(User user) {
        return false;
    }

/*
    private void createSession() throws CustomException {

        long ts = System.currentTimeMillis();

        try {
            // prepare  statement
            ps = connection.prepareStatement(Cnst.SQL_CREATE_SESSION, Statement.RETURN_GENERATED_KEYS);

            // set values for insert
            ps.setInt(1, GSession.getUserId());
            ps.setString(2, GSession.getSession());
            ps.setTimestamp(3, new Timestamp(ts));
            ps.setTimestamp(4, new Timestamp(ts));
            ps.executeQuery();
            GSession.setLastActivity(helper.getDateFromTimestamp(ts));


            ps.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to create or update client session", 500);
        }
    }*/
}
