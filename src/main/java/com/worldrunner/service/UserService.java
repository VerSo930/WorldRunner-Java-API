package com.worldrunner.service;

import javax.annotation.security.PermitAll;

import javax.annotation.security.RolesAllowed;
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
@Produces(Cnst.CONTENT_TYPE)
@Consumes(Cnst.CONTENT_TYPE)
public class UserService {

    private Response.ResponseBuilder rb;
    private UserDaoImpl dao;

    @PermitAll
    @GET
    @Path(Cnst.ENDPOINT_USERS + "/{id}")

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

            // Catch error, display error code and message from Exception
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
            resp.setError(2500);
        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }


    @RolesAllowed("ADMIN")
    @GET
    @Path(Cnst.ENDPOINT_USERS)
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

            // Catch error, display error code and message from Exception
            resp.setCode(Cnst.C_ERROR);
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
            resp.setError(2500);
        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }


    @PermitAll
    @POST
    @Path(Cnst.ENDPOINT_USERS)
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

            // Catch error, display error code and message from Exception
            resp.setCode(Cnst.C_ERROR);
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
            resp.setError(2500);
        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }

    // TODO: Method not working: write it?
    @PermitAll
    @PUT
    @Path(Cnst.ENDPOINT_USERS)
    public Response updateUserById(@NotNull User user) {

        MyResponse<User> resp = new MyResponse<>();

        try {
            // Check parameters
            ServiceTools.checkUser(user);

            // Create DAO, get user by id, create response Object
            dao = new UserDaoImpl();
            resp.setData( dao.updateUser(user) );
            resp.setCode(Cnst.C_REQ_OK);
            resp.setMessage(Cnst.MSG_UPDATE_USER);
            resp.setStatus(Cnst.SUCCESS);

        } catch (CustomException e) {

            // Catch error, display error code and message from Exception
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
            resp.setError(2500);
        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }


    @PermitAll
    @DELETE
    @Path(Cnst.ENDPOINT_USERS)
    public Response deleteUser(@NotNull User user) {

        MyResponse<User> resp = new MyResponse<>();

        try {
            // Check parameters
           // ServiceTools.checkUser(user);
            System.out.println(user.toString());

            // Create DAO, get user by id, create response Object
            dao = new UserDaoImpl();
            dao.deleteUser(user);
            resp.setData(null);
            resp.setCode(Cnst.C_REQ_OK);
            resp.setMessage(Cnst.MSG_DELETE_USER);
            resp.setStatus(Cnst.SUCCESS);

        } catch (CustomException e) {

            // Catch error, display error code and message from Exception
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
            resp.setStatus(Cnst.FAIL);
            resp.setError(2500);
        }

        // build response
        rb = Response.ok(resp);
        return rb.status(resp.getCode()).build();
    }




}