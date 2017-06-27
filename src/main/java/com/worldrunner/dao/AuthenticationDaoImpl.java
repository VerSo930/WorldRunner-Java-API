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
    private UserDao userDao;
    private Session GSession;
    private Helper helper;

    public AuthenticationDaoImpl() {
        //GSession = new Session();
        helper = new Helper();
    }

    @Override
    public Session authenticate(final User user) throws CustomException {
        GSession = new Session();
        try {
            connection = Database.getConnection();
            // prepare  statement
            ps = connection.prepareStatement(Cnst.SQL_AUTHENTICATION_CHECK_USER);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            ps.close();

            // Check user and password in database,
            // if exists we will continue with session, if not throw exception
            if(rs.next()){
                // assign user values
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstName"));
                user.setLastname(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setWeight(rs.getLong("weight"));
                user.setHeight(rs.getLong("height"));
                user.setCountry(rs.getLong("country"));
                user.setCreatedat(helper.formatTimestamp(rs.getTimestamp("createdat")));
                GSession.setUserId(user.getId());

                // If session exists we will fire an update, if not insert new session
                if(rs.getString("sessionId") != null){
                    GSession.setSession(rs.getString("sessionId"));
                    GSession.setLastActivity(rs.getString("lastActivity"));
                } else {
                    GSession.setSession(helper.generateSessionId());
                    //GSession.setLastActivity(helper.getDateFromTimestamp(System.currentTimeMillis()));
                }
                createSession();
            } else {
                throw new CustomException("wrong username or password", 500);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), 500);
        } finally {
            Database.close(connection);
        }

        return GSession;
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
    }
}
