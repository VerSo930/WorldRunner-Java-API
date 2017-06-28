package com.worldrunner;

import com.worldrunner.dao.AuthenticationDaoImpl;
import com.worldrunner.model.User;
import com.worldrunner.tools.Helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import javax.xml.bind.DatatypeConverter;
import java.sql.*;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        AuthenticationDaoImpl dao = new AuthenticationDaoImpl();

            parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUb2tlbiIsImlzcyI6IldvcmxkUnVubmVyIEFQSSIsInNlc3Npb25JZCI6InRlc3QxMjMiLCJpYXQiOjE0OTg1NTgyNzk2MjEsImV4cCI6MTQ5ODU2MTg3OTc4NX0.BpXT1SN6uh9tlZhMPZ8EhJz0mhWwNHYCSpSk_cP_ntY");
            System.out.println(System.currentTimeMillis());
            System.out.println(new Date(System.currentTimeMillis()).getTime());





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

    private static String generateJWT(String sessionId) {
        Date date = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setIssuer("WorldRunner API")
                .setSubject("Token")
                .claim("sessionId", sessionId)
                .claim("iat", date)
                .claim("exp", helper.addMinutesToCurrentDate(Cnst.JWT_EXPIRATION_TIME))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(Cnst.JWT_SECRET)
                )
                .compact();
    }
    private  String generateRefreshJWT(String sessionId) {
        Date date = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setIssuer("WorldRunner API")
                .setSubject("Token")
                .claim("sessionId", sessionId)
                .claim("iat", date)
                .claim("exp", Helper.addMinutesToCurrentDate(15))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(Cnst.JWT_SECRET)
                )
                .compact();
    }

    //Sample method to validate and read the JWT
    private static void parseJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(Cnst.JWT_SECRET))
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }

}
