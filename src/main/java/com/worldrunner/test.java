package com.worldrunner;

import com.worldrunner.model.User;

/**
 * Created by vuta on 16/06/2017.
 */
public class test {

    public static void main(String [] args) {

        User user1 = new User();
        user1.setId(23);

        User user2 = new User();
        user2.setId(23);


        if(user1.equals(user2)){
            System.out.print("OK!");
        }

    }


}
