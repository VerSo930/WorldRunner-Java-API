package com.worldrunner.dao;

import com.worldrunner.Cnst;
import com.worldrunner.model.step.Day;
import com.worldrunner.model.step.Step;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Database;
import com.worldrunner.tools.Helper;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by vuta on 16/06/2017.
 */
public class StepDaoImpl implements StepDao {

    private static Connection connection;
    private PreparedStatement ps;
    private List<Step> Gsteps;
    private Step Gstep = new Step();
    private List<Step> GlistStep = new ArrayList<>();

    public StepDaoImpl() {
        this.Gsteps = new ArrayList<Step>();
    }

    @Override
    public Step findById(int id, int page, int limit) throws CustomException {

        try {
            connection = Database.getConnection();
            // prepare  statement
            ps = connection.prepareStatement(Cnst.SQL_FIND_BY_ID_STEPS);
            ps.setInt(1, id);
            queryById();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), 500);
        } finally {
            Database.close(connection);
        }

        return Gstep;
    }

    @Override
    public List<Step> findAll(int page, int limit) throws CustomException {
        try {
            connection = Database.getConnection();
            // prepare  statement
            ps = connection.prepareStatement(Cnst.SQL_FINDALL_STEP);
            queryAll();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), 500);
        } finally {
            Database.close(connection);
        }

        return GlistStep;
    }


    @Override
    public List<Step> findByDate(String datetime, int page, int limit) throws Exception {
        if (Objects.equals(datetime, "24")) {
            try {
                connection = Database.getConnection();
                // prepare  statement
                ps = connection.prepareStatement("SELECT * FROM step WHERE userId = ? LIMIT 700");
                ps.setLong(1, 28);

                queryAll();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException(e.getMessage(), 500);
            } finally {
                Database.close(connection);
            }
        }
        return this.Gsteps;
    }

    @Override
    public Step insertStep(Step step) throws Exception {

        try {
            connection = Database.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement("START TRANSACTION;");
            psStep(step);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ps.close();
            connection.close();
        }
        return Gstep;
    }

    @Override
    public Step updateStep(Step step) throws Exception {
        return null;
    }

    @Override
    public void deleteStep(Step step) throws Exception {

    }

    private void queryById() throws CustomException {

        ResultSet rs;
        Helper helper = new Helper();

        try {

            rs = ps.executeQuery();
            Day day;
            int userId;

            // Get all steps from database
            while (rs.next()) {
                if (Gstep == null) {
                    Gstep = new Step();
                }
                Gstep.setUserId(rs.getInt("userId"));

                // init helper
                helper.initCalendar(rs);

                // set objects values
                day = new Day();
                day.setDate(rs.getDate("hour").toString());

                // add steps to specific date, specific
                Gstep.getDay(day).addStepsToList(rs.getInt("steps"), helper.getHourFromDate());

            }

            // Close statement
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), 500);
        } finally {
            Database.close(connection);
        }


    }

    private void queryAll() throws CustomException {

        ResultSet rs;
        Helper helper = new Helper();

        try {

            rs = ps.executeQuery();
            Step step;
            Day day;
            int userId;

            GlistStep = new ArrayList<>();
            HashMap<Integer, Step> integerStepHashMap = new HashMap<>();

            // Get all steps from database
            while (rs.next()) {

                // init helper
                helper.initCalendar(rs);

                // set objects values
                step = new Step();
                userId = rs.getInt("userId");
                step.setUserId(userId);
                day = new Day();
                day.setDate(rs.getDate("hour").toString());

                // check if object already exists
                if (integerStepHashMap.get(userId) != null) {
                    step = integerStepHashMap.get(userId);
                }

                // add steps to specific date, specific
                step.getDay(day).addStepsToList(rs.getInt("steps"), helper.getHourFromDate());
                integerStepHashMap.put(userId, step);

            }
            // put all values from hashmap into list
            GlistStep = new ArrayList<>(integerStepHashMap.values());

            // Close statement
            //ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), 500);
        } finally {
            //Database.close(connection);
        }


    }

    private void psStep(Step step) throws Exception {
        Helper helper = new Helper();
        int l = 0;

        // loop list of days
        for (Day d : step.getDayList()) {

            // loop each step list that exist inside day object
            for (int i = 0; i < d.getListSteps().size(); i++) {

                if (d.getListSteps().get(i) != null) {

                    // Configure calendar (date and hour for each row)
                    helper.initCalendar(d.getDate());
                    helper.getCalendar().set(Calendar.HOUR_OF_DAY, i);
                    helper.getCalendar().set(Calendar.MINUTE, 0);
                    helper.getCalendar().set(Calendar.SECOND, 0);

                    if(insertStepRow(step.getUserId(),d.getListSteps().get(i),helper)) {
                        d.getListSteps().set(i, null) ;
                }
            }

            // Check if number of rows affected is equal with number of provided elements

                ps.getConnection().commit();
                System.out.println("Insert OK");

            }
            step.getDayList().set(l, d);
            l++;
        }

        Gstep = step;

    }



    public boolean insertStepRow(int userId, int steps, Helper helper) throws Exception {

        ps = connection.prepareStatement("INSERT INTO step (userId, hour, steps) VALUES (?,?,?) ON DUPLICATE KEY UPDATE steps = ?");
        ps.setInt(1, userId);
        ps.setString(2, helper.getDateTime());
        ps.setInt(3, steps);
        ps.setInt(4, steps);

        return ps.executeUpdate() == 1;
    }

}