package com.worldrunner.dao;

/**
 * Created by Vuta Alexandru on 6/7/2017.
 */

import com.worldrunner.Cnst;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Database;
import com.worldrunner.model.User;
import com.worldrunner.tools.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserDaoImpl implements UserDao {

    private static Connection connection;
    private PreparedStatement ps;
    private List<User> users;


    public UserDaoImpl() {
        users = new ArrayList<>();

    }

    @Override
    public List<User> findAll() throws CustomException {

        try {
            connection = Database.getConnection();
            // prepare  statement
            ps = connection.prepareStatement(Cnst.SQL_FINDALL_USER);
            queryAll(ps);
            ps.close();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), 500);
        } finally {
            Database.close(connection);
        }

        return users;
    }

    @Override
    public User checkUserCredentials(User user) throws Exception {
        try {
            connection = Database.getConnection();
            // prepare  statement
            ps = connection.prepareStatement(Cnst.SQL_AUTHENTICATION_CHECK_USER);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
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
                user.setCreatedat(Helper.formatTimestamp(rs.getTimestamp("createdat")));

            } else {
                throw new CustomException("wrong username or password", 500);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), 500);
        } finally {
            Database.close(connection);
        }

        return user;
    }

    @Override
    public User findById(Long id) throws CustomException {

        try {

            connection = Database.getConnection();
            // prepare  statement
            ps = connection.prepareStatement(Cnst.SQL_FIND_USER);
            ps.setLong(1, id);
            queryAll(ps);
            ps.close();

        } catch (Exception e) {

            throw new CustomException(e.getMessage(), 500);

        } finally {
            // Close connection
            Database.close(connection);
        }

        if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new CustomException("user don't exist in database", 400);
        }
    }

    @Override
    public User insertUser(User user) throws CustomException {

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(Cnst.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setLong(5, user.getCountry());
            ps.setLong(6, user.getWeight());
            ps.setLong(7, user.getHeight());
            // Set id
            user.setId(executeUpdate("insert"));
            // Close statement
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Database.close(connection);
        }

        return user;
    }

    // TODO: Method update user is not ready yet!!
    @Override
    public User updateUser(User user) throws CustomException {

            try {
                connection = Database.getConnection();
                ps = connection.prepareStatement(Cnst.SQL_UPDATE_USER, Statement.RETURN_GENERATED_KEYS);

                // Set user intro prepared statement
                psUser(user);

                // Execute update
               executeUpdate("update");

                // Close statement
                ps.close();

            } catch (Exception e) {
                throw new CustomException(e.getMessage(), 520);
            } finally {
                Database.close(connection);
            }


        return user;
    }

    // TODO: Method delete user is not ready yet!!
    @Override
    public void deleteUser(User user) throws CustomException {


        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(Cnst.SQL_DELETE_USER);

            // Set user id into prepared statement
            ps.setLong(1, user.getId());

            // Execute update
            if(executeUpdate("delete") < 1) {
                throw new CustomException("user not deleted", 520);
            }

            // Close statement
            ps.close();

        } catch (Exception e) {
            throw new CustomException(e.getMessage(), 520);
        } finally {
            Database.close(connection);
        }


    }

    private void queryAll(PreparedStatement preparedStatement) throws CustomException {

        ResultSet rs;

        try {
            ps = preparedStatement;
            rs = ps.executeQuery();

            // Get all users from database
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstName"));
                user.setLastname(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setWeight(rs.getLong("weight"));
                user.setHeight(rs.getLong("height"));
                user.setCountry(rs.getLong("country"));
                user.setCreatedat(rs.getTimestamp("createdat").toString());
                users.add(user);
            }

            // Close statement/connection
            ps.close();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), 500);
        } finally {
            Database.close(connection);
        }

    }

    private Integer executeUpdate(String type) throws Exception {

        if (connection != null) {
            int count = ps.executeUpdate();

            if (Objects.equals(type, "update") || Objects.equals(type, "delete")) {
                return count;

            } else if (Objects.equals(type, "insert")) {

                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                return keys.getInt(1);
            }
        }

        throw new CustomException("No mysql connection", 500);

    }

    private void psUser(User user) throws CustomException {

        try {
            // Put user data in Prepared statement
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setLong(5, user.getCountry());
            ps.setLong(6, user.getWeight());
            ps.setLong(7, user.getHeight());

            // If update user is called, get the id from model
            // If is not set, insert new user was called (ID AUTO_INCREMENT)
            if (user.getId() != null) {
                ps.setLong(8, user.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("null fields?", 500);
        }


    }
}