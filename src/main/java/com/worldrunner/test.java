package com.worldrunner;

import com.worldrunner.dao.AuthenticationDaoImpl;
import com.worldrunner.model.Authentication.Session;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.*;
import java.sql.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by vuta on 16/06/2017.
 */
public class test {
static PreparedStatement ps;
static Connection connection;
   static Helper helper = new Helper();

    public static void main(String [] args) {
        User user = new User();
        user.setId(34);
        user.setEmail("verso.930@gmail.com");
        user.setPassword("verso9394");

        String date = helper.getDateFromTimestamp(System.currentTimeMillis());
        helper.getSqlTimestqmpFromString(date);
        System.out.println(helper.getSqlTimestqmpFromString(date).toString());



        try {
            connection = DriverManager.getConnection("jdbc:mariadb://vuta-alexandru.com:3306/spring_test?user=spring_test&password=spring123");
            insertStepRow(33, helper,50,true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AuthenticationDaoImpl dao = new AuthenticationDaoImpl();

            createSession();





        /*
        long start = System.currentTimeMillis();
        Key key = MacProvider.generateKey();



        String jwtStr = Jwts.builder()
                .setSubject("ME")
                .claim("user", "VerSo")
                .claim("country", "France")
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(
                                // This generated signing key is
                                // the proper length for the
                                // HS256 algorithm.
                                "pCu/ghCamq9+wS/CG16JJ1NBqur2Ckzl522AA8xbhSQ="
                        )
                )
                .compact();
        System.out.println(jwtStr);
        Jws<Claims> jws = Jwts.parser()
                .requireSubject("ME")
                .require("user", "VerSo")
                .require("country", "France")
                .setSigningKey(
                        TextCodec.BASE64.decode(
                                "pCu/ghCamq9+wS/CG16JJ1NBqur2Ckzl522AA8xbhSQ="
                        )
                )
                .parseClaimsJws(jwtStr);


        System.out.println(jws.toString());

        System.out.println(start - System.currentTimeMillis());
*/
    }

    private static void createSession()  {

        try {


        } catch(Exception e) {
            e.printStackTrace();
            //throw new CustomException(e.getMessage(), 500);
        }
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
