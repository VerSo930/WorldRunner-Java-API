package com.worldrunner.service;

import com.worldrunner.Cnst;
import com.worldrunner.dao.StepDaoImpl;
import com.worldrunner.dao.UserDaoImpl;
import com.worldrunner.model.*;
import com.worldrunner.model.step.Day;
import com.worldrunner.model.step.MyResponseStep;
import com.worldrunner.model.step.Step;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Database;
import com.worldrunner.tools.Helper;

import javax.annotation.security.PermitAll;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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


        MyResponseStep response = new MyResponseStep();
        response.setMessage("select steps");
        response.setStatus("success");
        response.setCode(200);
        response.setData(stepDao.findById(24, 0,20));

        // build response
        rb = Response.ok(response);
        return rb.status(200).build();
    }

    @PermitAll
    @GET
    @Path("/vuta")
    @Produces(Cnst.CONTENT_TYPE)
    public Response testMysql(@Context Request req) throws Exception {

       // testFnc();
        List<Step> steps = new ArrayList<>();
        List<Day> days = new ArrayList<>();
        Step step1,step2;

        Day day1, day2, day3;

        day1 = new Day();
        day2 = new Day();
        day3 = new Day();

        day1.setDate("1");
        day2.setDate("1");
        day3.setDate("2");

        step1 = new Step();
        step1.setUserId(1);
        step1.getDay(day1).getListSteps().add(1);
        step1.getDay(day1).getListSteps().add(2);
        step1.getDay(day1).getListSteps().add(3);
        step1.getDay(day2).getListSteps().add(4);
        step1.getDay(day2).getListSteps().add(5);
        step1.getDay(day2).getListSteps().add(6);





        // build response
        rb = Response.ok(step1);
        return rb.status(200).build();
    }
    @PermitAll
    @GET
    @Path("/add")
    @Produces(Cnst.CONTENT_TYPE)
    public Response addtestMysql(@Context Request req) throws Exception {

        testFnc();

        // build response
        rb = Response.ok("OK");
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
            for(int d=4; d<7; d++) {
                for (int h = 0; h < 24; h++) {
                    cal.set(Calendar.DAY_OF_MONTH, d);
                    cal.set(Calendar.HOUR_OF_DAY, h);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);


                    Date date = cal.getTime();
                    System.out.println(d + " " + formatter.format(date));


                    // Set user intro prepared statement
                    ps.setInt(1, 32);
                    ps.setTimestamp(2, new Timestamp(cal.getTimeInMillis()));
                    ps.setInt(3, random.nextInt(1000));

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

    private List<User> queryAll()  {
        PreparedStatement ps;
        ResultSet rs;
        Helper helper = new Helper();
        Connection connection = null;

        Day day = new Day();
        HashMap<Integer, User> usersMap = new HashMap<>();


        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(Cnst.SQL_FINDALL_STEP);
            rs = ps.executeQuery();
            User user;

            // Get all steps from database
            while (rs.next()) {
                helper.init(rs);
                user = new User();
                day = new Day();

            }


            // Close statement/connection
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Database.close(connection);
        }

    return new ArrayList<User>(usersMap.values());
    }
}
