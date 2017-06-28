package com.worldrunner.dao;

import com.worldrunner.model.Authentication.Authentication;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Helper;

import java.sql.*;

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
        //GSession = new Authentication();
        helper = new Helper();
    }

    @Override
    public User authenticate(final User user) throws CustomException {
return  null;
    }

    @Override
    public Authentication register(User user) {
        return null;
    }

    @Override
    public Authentication getSession(User user) {
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
            ps.setString(2, GSession.getAuthentication());
            ps.setTimestamp(3, new Timestamp(ts));
            ps.setTimestamp(4, new Timestamp(ts));
            ps.executeQuery();
            GSession.setLastActivity(helper.getDateFromTimestamp(ts));


            ps.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to create or update client authentication", 500);
        }
    }*/
}
