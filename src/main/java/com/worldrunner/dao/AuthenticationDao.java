package com.worldrunner.dao;

import com.worldrunner.model.Authentication.Session;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */
public interface AuthenticationDao {

    User authenticate(User user) throws CustomException;
    Session register(User user);
    Session getSession(User user);
    boolean checkSession(User user);


}
