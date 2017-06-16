package com.worldrunner.service;

import com.worldrunner.Cnst;
import com.worldrunner.dao.StepDao;
import com.worldrunner.dao.StepDaoImpl;
import com.worldrunner.dao.UserDaoImpl;
import com.worldrunner.model.*;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Database;

import javax.annotation.security.PermitAll;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by vuta on 15/06/2017.
 */
@ApplicationPath(Cnst.API_URL)
@Path(Cnst.API_URL)
public class StepsService {

    private Response.ResponseBuilder rb;
    private UserDaoImpl dao;

    @PermitAll
    @GET
    @Path(Cnst.ENDPOINT_STEPS)
    @Produces(Cnst.CONTENT_TYPE)
    public Response getAllSteps(@Context Request req) throws Exception {

        //testFnc();
        StepDaoImpl stepDao = new StepDaoImpl();
        StepPreMod stepPreMod = new StepPreMod();


        MyResponse<HashMap<User, List<Step>>> response = new MyResponse<>();
        response.setMessage("select steps");
        response.setStatus("success");
        response.setCode(200);
        response.setData(stepDao.findById(24, 0,20));

        // build response
        rb = Response.ok(response);
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
            ps = connection.prepareStatement("INSERT INTO step ( userId, distance, hour, steps) VALUES (?, ?, ?, ?)");
            for(int d=1; d<24; d++) {
                for (int h = 0; h < 24; h++) {
                    cal.set(Calendar.DAY_OF_MONTH, d);
                    cal.set(Calendar.HOUR_OF_DAY, h);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);

                    Date date = cal.getTime();
                    System.out.println(d + " " + formatter.format(date));


                    // Set user intro prepared statement
                    ps.setInt(1, 28);
                    ps.setDouble(2, random.nextDouble());
                    ps.setTimestamp(3, new Timestamp(cal.getTimeInMillis()));
                    ps.setInt(4, random.nextInt(1000));

                    // Execute update
                    ps.executeUpdate();
                }
            }
            // Close statement
            ps.close();
            Database.close(connection);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
