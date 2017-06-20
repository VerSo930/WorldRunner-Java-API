package com.worldrunner;

import com.worldrunner.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vuta on 16/06/2017.
 */
public class test {

    public static void main(String [] args) {
        User user = new User();
        user.setId(32);
        List<User> users = new ArrayList<>();
        users.add(user);

        System.out.println(users.get(0));
        user.setId(222);
        System.out.println(users.get(0));
    }


}
