package com.worldrunner.service;

import com.worldrunner.Cnst;
import com.worldrunner.dao.UserDaoImpl;
import com.worldrunner.model.Authentication.Authentication;
import com.worldrunner.model.MyResponse;
import com.worldrunner.model.User;
import com.worldrunner.security.Tokenizer;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Helper;
import com.worldrunner.tools.ServiceTools;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.jboss.resteasy.util.Base64;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */

@Path(Cnst.API_URL)
@Produces(Cnst.CONTENT_TYPE)
@Consumes(Cnst.CONTENT_TYPE)
public class LoginRegister {

    private Response.ResponseBuilder rb;
    private UserDaoImpl dao;

    @PermitAll
    @POST
    @Path("/authorization/login")
    public Response login(@HeaderParam("Authorization") String authorization) {
        MyResponse<Authentication> response;
        dao = new UserDaoImpl();
        Authentication authentication;

        try {
            String[] usernameAndPassword = parseHeaderToken(authorization);
            User user = dao.checkUserCredentials(new User(usernameAndPassword[0], Helper.cryptPassword(usernameAndPassword[1])));
            // generate authentication object (token and user)
            authentication = new Authentication(Tokenizer.generateJWT(user.getId(), user.getRole()), user);
            // set response data
            response = new MyResponse<>(Cnst.SUCCESS, 0, 200,"authentication successfully", authentication);

        } catch (Exception e) {
            response = new MyResponse<>(Cnst.FAIL, 2500, 401,e.getMessage(),null);
        }

        // build response
        rb = Response.ok(response);
        return rb.status(200).build();
    }

    @PermitAll
    @POST
    @Path("/authorization/register")
    public Response register(User user) {

        MyResponse<Authentication> response = new MyResponse<>();
        Authentication authentication;

        try {

            // Check for empty & null params
            ServiceTools.checkUser(user);
            // Create DAO, insert new user
            dao = new UserDaoImpl();
            user = dao.insertUser(user);
            // Create authentication and generate TOKEN
            authentication = new Authentication(Tokenizer.generateJWT(user.getId(), user.getRole()), user);
            // Set response data to object
            response.setData(authentication);
            response.setCode(Cnst.C_REQ_OK);
            response.setMessage(Cnst.MSG_INSERT_USER);
            response.setStatus(Cnst.SUCCESS);

        } catch (Exception e) {

            // Catch error, display error code and message from Exception
            response.setCode(Cnst.C_ERROR);
            response.setMessage(e.getMessage());
            response.setStatus(Cnst.FAIL);
            response.setError(2500);
        }

        // build response
        rb = Response.ok(response);
        return rb.status(200).build();
    }


    private String[] parseHeaderToken(String token) throws CustomException {
        try {
            String[] string = new String[2];
            String usernameAndPassword;
            token = token.replaceFirst("Basic" + " ", "");

            usernameAndPassword = new String(Base64.decode(token));

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            string[0] = tokenizer.nextToken();
            string[1] = tokenizer.nextToken();

            return string;
        } catch (Exception e) {
            throw new CustomException("Not allowed [ NO AUTH PROVIDED ]", 405);
        }

    }


}
