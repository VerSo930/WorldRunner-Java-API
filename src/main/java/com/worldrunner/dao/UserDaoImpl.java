package com.worldrunner.dao;

/**
 * Created by Vuta Alexandru on 6/7/2017.
 */

import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Database;
import com.worldrunner.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private static Connection connection;
    private PreparedStatement ps;
    private List<User> users;


    public UserDaoImpl()  {
        users = new ArrayList<>();

    }


    @Override
    public List<User> findAll() throws Exception {

        connection = Database.getConnection();
        // prepare  statement
        ps = connection.prepareStatement("SELECT * FROM user");
        queryAll(ps);

        // Close statement/connection
        ps.close();
        Database.close(connection);

        return users;
    }

    @Override
    public User findById(Long id) throws Exception {
        connection = Database.getConnection();
        // prepare  statement
        ps = connection.prepareStatement("SELECT * FROM user WHERE id =?");
        ps.setLong(1, id);
        queryAll(ps);

        // Close statement/connection
        ps.close();
        Database.close(connection);

        return (users == null) ? null : users.get(0);
    }

    @Override
    public User insertUser(User user) throws Exception {
        connection = Database.getConnection();
        // prepare  statement
        ps = connection.prepareStatement("INSERT INTO user (firstName, lastName, email, password, country, weight, height) VALUE (?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, user.getFirstname());
        ps.setString(2, user.getLastname());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());
        ps.setLong(5, user.getCountry());
        ps.setLong(6, user.getWeight());
        ps.setLong(7, user.getHeight());
        modify(ps);

        // Close statement/connection
        ps.close();
        Database.close(connection);

        return user;
    }

    // TODO: Method update user is not ready yet!!
    @Override
    public User updateUser(User user) {

        return user;
    }

    // TODO: Method delete user is not ready yet!!
    @Override
    public void deleteUser(User user) {

    }


    private void queryAll(PreparedStatement preparedStatement) throws Exception {

        ResultSet rs;
        if (connection != null) {

            ps = preparedStatement;
            rs = ps.executeQuery();

            // Get all users from database
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString("firstName"));
                user.setLastname(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setWeight(rs.getLong("weight"));
                user.setHeight(rs.getLong("height"));
                user.setCountry(rs.getLong("country"));
                users.add(user);
            }
        }

        // Close statement/connection
        ps.close();
        Database.close(connection);

    }


    private void modify(PreparedStatement preparedStatement) throws Exception {

        connection = Database.getConnection();

        if (connection != null) {
            preparedStatement.executeUpdate();
        }

    }
}