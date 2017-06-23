package com.worldrunner.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by vuta on 16/06/2017.
 */
public class Helper {

    // private static SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static GregorianCalendar cal;

    public void init(String date) {

        try {
            cal.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Helper() {
        cal = new GregorianCalendar();
    }

    public void init(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("hour");
        cal.setTime(timestamp);
    }

    public String getDate(){
        return df.format(cal.getTime());
    }

    public String getDateTime() {
        return dtf.format(cal.getTime());
    }


    public GregorianCalendar getCalendar(){
        return cal;
    }

    public int getHourFromDate() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

}
