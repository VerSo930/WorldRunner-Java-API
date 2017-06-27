package com.worldrunner.dao;


import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;

import java.util.List;

/**
 * Created by vuta on 08/06/2017.
 */
public interface UserDao {

    List<User> findAll() throws  Exception;
    User checkUserCredentials(User user) throws Exception;
    User findById(Long id) throws  Exception;
    User insertUser(User user) throws Exception;
    User updateUser(User user  ) throws  Exception;
    void deleteUser(User user) throws  Exception;
}
