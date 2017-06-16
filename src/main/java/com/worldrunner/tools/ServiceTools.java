package com.worldrunner.tools;

import com.worldrunner.Cnst;
import com.worldrunner.dao.UserDaoImpl;
import com.worldrunner.model.MyResponse;
import com.worldrunner.model.User;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Vuta Alexandru on 6/13/2017.
 */
public class ServiceTools {

    public static void checkUser(User u) throws CustomException {
        if(em(u.getEmail()) || em(u.getFirstname()) || em(u.getLastname()) || em(u.getPassword()) || u.getEmail().length() < Cnst.EMAIL_MIN ||
                u.getEmail().length() > Cnst.EMAIL_MAX || u.getFirstname().length() < Cnst.FIRST_NAME_MIN || u.getFirstname().length() > Cnst.FIRST_NAME_MAX
                || u.getLastname().length() < Cnst.LAST_NAME_MIN  || u.getLastname().length() > Cnst.LAST_NAME_MAX || u.getPassword().length() < Cnst.PASSWORD_MIN
                || u.getPassword().length() > Cnst.PASSWORD_MAX)
            throw  new  CustomException("user parameters not set or empty", 400);
    }

    public  static void checkId(Object id) throws CustomException {
        if((Object)(id).getClass() != Integer.class) {
            throw  new  CustomException("user parameters not set or empty", 400);
        }
    }

    private static boolean em( final String s ) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }
}
