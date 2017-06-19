package com.worldrunner.dao;

import com.worldrunner.Cnst;
import com.worldrunner.model.step.Day;
import com.worldrunner.model.step.MyResponseStep;
import com.worldrunner.model.step.Step;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Database;
import com.worldrunner.tools.Helper;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by vuta on 16/06/2017.
 */
public class StepDaoImpl implements StepDao {

    private static Connection connection;
    private PreparedStatement ps;
    private List<Step> steps;
    HashMap<User, HashMap<Integer, List<Integer>>> stepsList = new HashMap<>();

    public StepDaoImpl() {
        this.steps = new ArrayList<Step>();
    }

    @Override
    public HashMap<User, HashMap<Integer, List<Integer>>> findById(int id, int page, int limit) throws Exception {
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

        return stepsList;
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
        return this.steps;
    }

    @Override
    public User insertStep(Step step) throws Exception {
        return null;
    }

    @Override
    public User updateStep(Step step) throws Exception {
        return null;
    }

    @Override
    public void deleteStep(Step step) throws Exception {

    }

    private void queryAll() throws CustomException {

        ResultSet rs;
        Helper helper = new Helper();
        int day = 0;
        int lastday = 0;

        User user = new User();
        HashMap<Integer, List<Integer>> listDay = new HashMap<>();
        List<Integer> listSteps = new ArrayList<>();
        //Map<java.sql.Date, List<Day>> dayList = new HashMap<>();


        try {

            rs = ps.executeQuery();

            // Get all steps from database
            while (rs.next()) {
                helper.init(rs);
                day = helper.getStepDay();
                // set user id
                user.setId(rs.getInt("userId"));


                if (stepsList.get(user) != null) {
                    // if list of steps-days exist for this user, get it
                    listDay = stepsList.get(user);

                    if (listDay.get(rs.getDate("hour")) != null) {
                        // if list steps for this day exist, get it
                        listSteps = listDay.get(day);
                        listSteps.add(rs.getInt("steps"));
                        listDay.put(day, listSteps);
                    } else {
                        // if list steps for this date don't exist, create it
                        listSteps = new ArrayList<>();
                        listSteps.add(rs.getInt("steps"));
                        listDay.put(day, listSteps);
                    }

                    listDay.put(day, listSteps);

                } else {
                    // if list of steps-days don't exist for this user, create it
                    listDay = new HashMap<>();
                    // if list steps for this date don't exist, create it
                    listSteps = new ArrayList<>();
                    listSteps.add(rs.getInt("steps"));
                    listDay.put(day, listSteps);

                }
                stepsList.put(user, listDay);
                lastday = day;
            }

            // Close statement/connection
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), 500);
        } finally {
            Database.close(connection);
        }


    }

}
