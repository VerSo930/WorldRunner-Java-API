package com.worldrunner.tools;

import com.worldrunner.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by vuta on 16/06/2017.
 */
public class Helper {

    // private static SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static GregorianCalendar cal;

    public void initCalendar(String date) {

        try {
            cal.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Helper() {
        cal = new GregorianCalendar();
    }

    public void initCalendar(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("hour");
        cal.setTime(timestamp);
    }

    public void initCalendar() {
       cal =  new GregorianCalendar();
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

    public String getDateFromTimestamp(long timestamp) {
        return dtf.format(timestamp);
    }

    public static String formatTimestamp(Timestamp timestamp){
        return dtf.format(timestamp);
    }

    public Timestamp getSqlTimestqmpFromString(String date){
        try {
            return new Timestamp(dtf.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date addMinutesToCurrentDate (int minutes) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = new Date(System.currentTimeMillis()).getTime();
        return new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
    }

    public static String cryptPassword(String pass) throws  CustomException{
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new CustomException(ex.getMessage(), 500);
        }

    }

    public boolean checkPassword(String crypted, String password) throws Exception{
        return cryptPassword(password).equals(crypted);
    }

    public String generateSessionId(){
        return UUID.randomUUID().toString();
    }
/**
    Here are some methods for login / register
    like check if user exists, if is valid, create and update authentication creditals
**/
    public boolean checkUserCredentials(final User user) {
return false;
    }

}
