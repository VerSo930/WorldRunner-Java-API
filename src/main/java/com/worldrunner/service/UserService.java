package com.worldrunner.service;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.sun.istack.NotNull;
import com.worldrunner.Cnst;
import com.worldrunner.dao.UserDaoImpl;
import com.worldrunner.model.MyResponse;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.ServiceTools;

import java.util.List;


@Path(Cnst.API_URL)
public class UserService {

    private Response.ResponseBuilder rb;
    private UserDaoImpl dao;

    @PermitAll
    @GET
    @Path(Cnst.ENDPOINT_USERS + "/{id}")
    @Produces(Cnst.CONTENT_TYPE)
    public Response getUserById(@PathParam("id") int id, @Context Request req) {

        MyResponse<User> resp = new MyResponse<>();

        try {

            // Create DAO, get user by id, create response Object
            dao = new UserDaoImpl();
            resp.setData(dao.findById((long) id));
            resp.setCode(Cnst.C_REQ_OK);
            resp.setMessage(Cnst.MSG_FIND_BY_ID);
            resp.setStatus(Cnst.SUCCESS);

        } catch (CustomException e) {

            // Catch error, dysplay error code and message from Exception
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }


    @PermitAll
    @GET
    @Path(Cnst.ENDPOINT_USERS)
    @Produces(Cnst.CONTENT_TYPE)
    public Response getAllUsers(@Context Request req) throws Exception {

        MyResponse<List<User>> resp = new MyResponse<>();

        try {

            // Create DAO, get user by id, create response Object
            dao = new UserDaoImpl();
            resp.setData(dao.findAll());
            resp.setCode(Cnst.C_REQ_OK);
            resp.setMessage(Cnst.MSG_FIND_ALL);
            resp.setStatus(Cnst.SUCCESS);

        } catch (Exception e) {

            // Catch error, dysplay error code and message from Exception
            resp.setCode(Cnst.C_ERROR);
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }


    @PermitAll
    @POST
    @Path(Cnst.ENDPOINT_USERS)
    @Produces(Cnst.CONTENT_TYPE)
    @Consumes(Cnst.CONTENT_TYPE)
    public Response insertUser(User user) throws Exception {

        MyResponse<User> resp = new MyResponse<>();

        try {

            // Check for empty & null params
            ServiceTools.checkUser(user);
            // Create DAO, get user by id, create response Object
            dao = new UserDaoImpl();
            resp.setData(dao.insertUser(user));
            resp.setCode(Cnst.C_REQ_OK);
            resp.setMessage(Cnst.MSG_FIND_ALL);
            resp.setStatus(Cnst.SUCCESS);

        } catch (Exception e) {

            // Catch error, dysplay error code and message from Exception
            resp.setCode(Cnst.C_ERROR);
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }

    // TODO: Method not working: write it?
    @PermitAll
    @PUT
    @Path(Cnst.ENDPOINT_USERS)
    @Produces(Cnst.CONTENT_TYPE)
    @Consumes(Cnst.CONTENT_TYPE)
    public Response updateUserById(@NotNull User user) {

        MyResponse<User> resp = new MyResponse<>();

        try {

            // Create DAO, get user by id, create response Object
            dao = new UserDaoImpl();
            resp.setData( dao.updateUser(user) );
            resp.setCode(Cnst.C_REQ_OK);
            resp.setMessage(Cnst.MSG_UPDATE_USER);
            resp.setStatus(Cnst.SUCCESS);

        } catch (Exception e) {

            // Catch error, dysplay error code and message from Exception
            resp.setCode(Cnst.C_ERROR);
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }



}