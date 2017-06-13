package com.worldrunner.service;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;


import com.sun.istack.NotNull;
import com.worldrunner.Constants;
import com.worldrunner.dao.UserDaoImpl;
import com.worldrunner.model.MyResponse;
import com.worldrunner.model.User;

@Path(Constants.API_URL)
public class UserService {

    @PermitAll
    @GET
    @Path(Constants.ENDPOINT_USERS + "/{id}")
    @Produces(Constants.CONTENT_TYPE)
    public Response getUserById(@PathParam("id") int id, @Context Request req) {

        MyResponse<User> resp = new MyResponse<>();
        UserDaoImpl dao = null;

        try {
            dao = new UserDaoImpl();
            resp.setObj(dao.findById((long) id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Response.ResponseBuilder rb = Response.ok(resp);
        return rb.build();
    }

    @PermitAll
    @GET
    @Path(Constants.ENDPOINT_USERS)
    @Produces(Constants.CONTENT_TYPE)
    public Response getUser(@Context Request req) {

        MyResponse resp = new MyResponse();
        UserDaoImpl user = null;

        try {
            user = new UserDaoImpl();
            resp.setObj(user.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Response.ResponseBuilder rb = Response.ok(resp);
        return rb.build();
    }

    // TODO: Method not working: write it?
    @PermitAll
    @PUT
    @Path(Constants.ENDPOINT_USERS)
    @Produces(Constants.CONTENT_TYPE)
    public Response updateUserById(@NotNull User user) {

        MyResponse resp = new MyResponse();
        Response.ResponseBuilder rb;
        resp.setObj(user);

        if (resp.checkUser()) {
            UserDaoImpl daoOp = null;
            try {
                daoOp = new UserDaoImpl();
                daoOp.insertUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        rb = Response.status(resp.getCode()).entity(resp);
        return rb.build();
    }


}