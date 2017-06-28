package com.worldrunner.security;

import com.worldrunner.Cnst;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Set;

/**
 * Created by vuta on 28/06/2017.
 */

public class Tokenizer {

    public static void verifyJWT(String jwt, Set<String> roles) throws CustomException {

        try {
            //This will throw an exception if it is not a signed JWS (as expected)
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(Cnst.JWT_SECRET))
                    .parseClaimsJws(jwt).getBody();

            // If token doesn't contain the requested role, throw exception
            if(!roles.contains(claims.getAudience()))
            {
                throw new Exception();
            }

            // TODO: Delete after debug finished
            System.out.println("ID: " + claims.getId());
            System.out.println("Audience: " + claims.getAudience());
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("Expiration: " + claims.getExpiration());

        } catch (Exception e){
            throw new CustomException( "Not allowed [ BAD SIGNATURE ]", 401);
        }

    }

    public static String generateJWT(int userId, String email, String role) {
        Date date = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setIssuer("WorldRunner API")
                .setSubject("Token")
                .setAudience("USER")
                .setIssuedAt(date)
                .setExpiration(Helper.addMinutesToCurrentDate(Cnst.JWT_EXPIRATION_TIME))
                .claim("userId", userId)
                .claim("email:",email)
                .signWith(SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(Cnst.JWT_SECRET)
                )
                .compact();
    }
}
