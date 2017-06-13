package com.worldrunner.dao;


import com.worldrunner.model.User;

import java.util.List;

/**
 * Created by vuta on 08/06/2017.
 */
public interface UserDao {

    List<User> findAll() throws  Exception;
    User findById(Long id) throws  Exception;
    void insertUser(User employee) throws  Exception;
    void updateUser(User employee) throws  Exception;
    void deleteUser(User employee) throws  Exception;
}
