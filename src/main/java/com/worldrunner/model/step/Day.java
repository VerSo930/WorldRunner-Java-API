package com.worldrunner.model.step;

import com.worldrunner.tools.Helper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vuta on 19/06/2017.
 */

public class Day {

    private String date;
    private List<Integer> listSteps;

    public Day() {
        listSteps = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            listSteps.add(i, null);
        }
    }

    public List<Integer> getListSteps() {
        return listSteps;
    }

    public void addStepsToList(int numberOfSteps, int hourIndex) {
        listSteps.set(hourIndex, numberOfSteps);
    }

    public void setListSteps(List<Integer> listSteps) {
        this.listSteps = listSteps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public boolean eq(Day obj) {
        return this.getDate().equals(obj.getDate());
    }
}