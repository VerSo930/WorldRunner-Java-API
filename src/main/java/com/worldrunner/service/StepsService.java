package com.worldrunner.service;

import com.worldrunner.Cnst;
import com.worldrunner.dao.StepDaoImpl;
import com.worldrunner.dao.UserDaoImpl;
import com.worldrunner.model.*;
import com.worldrunner.model.step.Step;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Database;
import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by vuta on 15/06/2017.
 */

@Path(Cnst.API_URL)
@Produces(Cnst.CONTENT_TYPE)
@Consumes(Cnst.CONTENT_TYPE)
public class StepsService {

    private Response.ResponseBuilder rb;
    private UserDaoImpl dao;

    @PermitAll
    @GET
    @Path("/test")
    public Response test(@Context Request req) {
        //testFnc();
        rb = Response.ok("ok");
        return rb.status(200).build();
    }

    @PermitAll
    @POST
    @Path(Cnst.ENDPOINT_STEPS)
    public Response insertStep(@Context Request req, Step step) {
        StepDaoImpl dao = new StepDaoImpl();
        MyResponse<Step> myResponse = new MyResponse<>();
        try {
            myResponse.setCode(200);
            myResponse.setMessage("OK");
            myResponse.setData(dao.insertStep(step));

        } catch (Exception e) {
            myResponse.setCode(500);
            e.printStackTrace();
            myResponse.setMessage(e.getMessage());
        }

        rb = Response.ok(myResponse);
        return rb.status(200).build();
    }

    @PermitAll
    @GET
    @Path(Cnst.ENDPOINT_STEPS+"/{id}")
    public Response getStepsById(@Context Request req, @PathParam("id") int id) {

        StepDaoImpl dao = new StepDaoImpl();
        MyResponse<Step> myResponse = new MyResponse<>();

        // build response
        try {
            myResponse.setCode(200);
            myResponse.setMessage("OK");
            myResponse.setData(dao.findById(id,1,1));

        } catch (CustomException e) {
            myResponse.setCode(500);
            myResponse.setMessage("database error");
        }
        rb = Response.ok(myResponse);
        return rb.status(200).build();
    }

    @PermitAll
    @GET
    @Path(Cnst.ENDPOINT_STEPS)
    @Produces(Cnst.CONTENT_TYPE)
    public Response getAllSteps(@Context Request req)  {


        //testFnc();
        StepDaoImpl dao = new StepDaoImpl();
        MyResponse<List<Step>> myResponse = new MyResponse<>();
        // build response
        try {
            myResponse.setCode(200);
            myResponse.setMessage("OK");
            myResponse.setData(dao.findAll(1,1));

        } catch (Exception e) {

            myResponse.setCode(500);
            myResponse.setMessage("database error");

        }
        rb = Response.ok(myResponse);
        return rb.status(200).build();
    }

    public void testFnc() {
        Connection connection;
        Random random = new Random();
        PreparedStatement ps;

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar cal=new GregorianCalendar();


        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("INSERT INTO step ( userId, hour, steps) VALUES (?, ?, ?)");
            for (int i = 28; i <29 ; i++) {


                for(int d=1; d<17; d++) {
                    for (int h = 0; h < 24; h++) {
                        cal.set(Calendar.DAY_OF_MONTH, d);
                        cal.set(Calendar.HOUR_OF_DAY, h);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);

                        Date date = cal.getTime();
                        System.out.println(d + " " + formatter.format(date));


                        // Set user intro prepared statement
                        ps.setInt(1, i);
                        ps.setTimestamp(2, new Timestamp(cal.getTimeInMillis()));
                        ps.setInt(3, random.nextInt(1000));

                        // Execute update
                        ps.executeUpdate();
                    }
                }}
            // Close statement
            ps.close();
            Database.close(connection);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}