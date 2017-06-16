package com.worldrunner.dao;

import com.worldrunner.Cnst;
import com.worldrunner.model.Step;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Database;
import com.worldrunner.tools.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by vuta on 16/06/2017.
 */
public class StepDaoImpl implements StepDao {

    private static Connection connection;
    private PreparedStatement ps;
    private List<Step> steps;
    HashMap<User, List<Step>> userListHashMap2 = new HashMap<>();

    public StepDaoImpl() {
        this.steps = new ArrayList<Step>();
    }

    @Override
    public HashMap<User, List<Step>> findById(int id, int page, int limit) throws Exception {
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

        return userListHashMap2;
    }


    @Override
    public List<Step> findByDate(String datetime, int page, int limit) throws Exception {
        if(Objects.equals(datetime, "24")) {
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
        Helper helper =  new Helper();

        try {

            rs = ps.executeQuery();
            Integer lastday = null;
            Integer day;
            Integer userId = 0;
            Double distance = 0D;

            User user = new User();

            HashMap<User, List<Step>> userListHashMap = new HashMap<>();

            Step stepObj = new Step();
            List<Step>  stepObjlist = new ArrayList<>();
            List<Integer> steps24List = new ArrayList<>();


            // Get all steps from database
            while (rs.next()) {

                userId = rs.getInt("userId");
                user.setId(userId);

                // initialize helper and get day of timestamp from mysql row
                helper.init(rs);
                day = helper.getStepDay();


                if (Objects.equals(lastday, day)) {
                    stepObj.addSteps(rs.getInt("steps"));
                }

                if (!Objects.equals(lastday, day)) {
                    stepObj = new Step();
                    stepObj.setDay(rs.getTimestamp("hour"));
                    stepObjlist.add(stepObj);

                }

                // Reset last day
                lastday = day;
                userListHashMap.put(user, stepObjlist);
            }
            userListHashMap2 = userListHashMap;
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
