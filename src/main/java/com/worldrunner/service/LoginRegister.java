package com.worldrunner.service;

import com.worldrunner.Cnst;
import com.worldrunner.dao.AuthenticationDaoImpl;
import com.worldrunner.dao.UserDaoImpl;
import com.worldrunner.model.Authentication.AuthorizationResponse;
import com.worldrunner.model.Authentication.Session;
import com.worldrunner.model.MyResponse;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Helper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.jboss.resteasy.util.Base64;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */

@Path(Cnst.API_URL)
@Produces(Cnst.CONTENT_TYPE)
@Consumes(Cnst.CONTENT_TYPE)
public class LoginRegister {
    Response.ResponseBuilder rb;
    AuthenticationDaoImpl dao;


    @PermitAll
    @POST
    @Path("/authorization/login")

    public Response login(@HeaderParam("Authorization") String authorization) {
        MyResponse<Session> response;
        dao = new AuthenticationDaoImpl();
        String[] usernameAndPassword = parseHeaderToken(authorization);
        System.out.println(usernameAndPassword[0]);
        System.out.println(usernameAndPassword[1]);

        try {
            Session session = dao.authenticate(new User(usernameAndPassword[0], Helper.cryptPassword(usernameAndPassword[1])));
            session.setToken(generateJWT(session.getUserId(),session.getSession()));

            response = new MyResponse<>(Cnst.SUCCESS, 0, 200,"authentication successfully", session);

        } catch (CustomException e) {
            response = new MyResponse<>(Cnst.FAIL, 2500, 401,e.getMessage(),null);
        }

        // build response
        rb = Response.ok(response);
        return rb.status(200).build();
    }

   /* @RolesAllowed("ADMIN")
    @POST
    @Path("/authorization/login")
    public Response refreshToken(@HeaderParam("X-Session") String sessionId, @Context Request req, String token) {

        MyResponse<Session> resp = new MyResponse<>();


        try {

            //resp.setData(dao.authenticate(user));
            resp.setCode(Cnst.C_REQ_OK);
            resp.setMessage(sessionId);
            resp.setStatus(Cnst.SUCCESS);

        } catch (Exception e) {

            // Catch error, display error code and message from Exception
            resp.setCode(405);
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
            resp.setError(2500);

        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }
*/
    public String[] parseHeaderToken(String token)  {
        String [] string = new String[2];
        String usernameAndPassword;
        token = token.replaceFirst("Basic" + " ", "");
        try {
            usernameAndPassword = new String(Base64.decode(token));
        } catch (Exception e) {
            return null;
        }

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");

        string[0] = tokenizer.nextToken();
        string[1] = tokenizer.nextToken();
        return string;

    }

    private String generateJWT(int userId, String userEmail) {
        Date date = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setIssuer("WorldRunner API")
                .setSubject("Token")
                .claim("userId", userId)
                .claim("userEmail", userEmail)
                .claim("iat", date)
                .claim("exp", Helper.addMinutesToCurrentDate(Cnst.JWT_EXPIRATION_TIME))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(Cnst.JWT_SECRET)
                )
                .compact();
    }
}
