package com.worldrunner;

import com.worldrunner.model.User;
import com.worldrunner.tools.Helper;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vuta on 16/06/2017.
 */
public class test {
static PreparedStatement ps;
static Connection connection;

    public static void main(String [] args) {

        Helper helper = new Helper();
        helper.init("2017-06-24");
        helper.getCalendar().set(Calendar.HOUR_OF_DAY, 0);
        helper.getCalendar().set(Calendar.MINUTE, 1);
        helper.getCalendar().set(Calendar.SECOND, 0);

        try {
            connection = DriverManager.getConnection("jdbc:mariadb://vuta-alexandru.com:3306/spring_test?user=spring_test&password=spring123");
            insertStepRow(33, helper,50,true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(helper.getDateTime());

    }

    private static boolean checkIfStepHourExist(String date) throws Exception {

        ps = connection.prepareStatement("SELECT id FROM step WHERE TIMESTAMP (hour) = ?", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, date);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    private static boolean checkIfDateExist(String date) throws Exception {

        ps = connection.prepareStatement("SELECT id FROM step WHERE DATE (hour) = ?", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, date);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }


    public static boolean insertStepRow(int userId, Helper helper, int steps, boolean dateExist) throws Exception {

        if (!checkIfStepHourExist(helper.getDateTime())) {
            ps = connection.prepareStatement("INSERT INTO step (userId,steps, hour) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        } else {
            ps = connection.prepareStatement("UPDATE step SET userId = ?, steps = ? WHERE TIMESTAMP (hour) = ?", Statement.RETURN_GENERATED_KEYS);
        }
        ps.setInt(1, userId);
        ps.setString(3, helper.getDateTime());
        ps.setInt(2, steps);
        return ps.executeUpdate() == 1;
    }



}
