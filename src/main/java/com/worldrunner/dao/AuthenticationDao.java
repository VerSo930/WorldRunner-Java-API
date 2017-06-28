package com.worldrunner.dao;

import com.worldrunner.model.Authentication.Authentication;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */
public interface AuthenticationDao {

    User authenticate(User user) throws CustomException;
    Authentication register(User user);
    Authentication getSession(User user);
    boolean checkSession(User user);


}
