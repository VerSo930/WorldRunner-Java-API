package com.worldrunner.dao;

import com.worldrunner.model.Step;
import com.worldrunner.model.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vuta on 16/06/2017.
 */
public interface StepDao {

    HashMap<User, List<Step>> findById(int id, int page, int limit) throws  Exception;
    List<Step> findByDate(String datetime, int page, int limit) throws  Exception;
    User insertStep(Step step) throws Exception;
    User updateStep(Step step) throws  Exception;
    void deleteStep(Step step) throws  Exception;
}
