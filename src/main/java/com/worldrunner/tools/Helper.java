package com.worldrunner.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by vuta on 16/06/2017.
 */
public class Helper {

    // private static SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static GregorianCalendar cal;

    public Helper() {
        cal = new GregorianCalendar();
    }

    public void init(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("hour");
        cal.setTime(timestamp);
    }

    public int getStepDay() throws SQLException {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getHourFromDate() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

}
