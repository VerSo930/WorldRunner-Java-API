package com.worldrunner.service;

import com.worldrunner.Cnst;
import com.worldrunner.dao.AuthenticationDaoImpl;
import com.worldrunner.dao.UserDaoImpl;
import com.worldrunner.model.Authentication.Session;
import com.worldrunner.model.MyResponse;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.security.Key;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */

@Path(Cnst.API_URL)
@Produces(Cnst.CONTENT_TYPE)
@Consumes(Cnst.CONTENT_TYPE)
public class LoginRegister {
    Response.ResponseBuilder rb;
    AuthenticationDaoImpl dao;


    @RolesAllowed("ADMIN")
    @POST
    @Path("/authorization/login")

    public Response login(@HeaderParam("X-Session") String sessionId,@Context Request req, User user) {
        MyResponse<Session> resp = new MyResponse<>();
        dao=new AuthenticationDaoImpl();



        try {
            // Create DAO, get user by id, create response Object
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
        return rb.status(200).build();
    }
}
