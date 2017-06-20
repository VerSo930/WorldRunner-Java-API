package com.worldrunner.model;

import com.worldrunner.model.step.Step;

import java.util.List;

/**
 * Created by vuta on 16/06/2017.
 */
public class StepPreMod {

    public User user;
    public List<Step> steps;

    public StepPreMod() {
        this.user = null;
        this.steps = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
