package com.worldrunner.model.step;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vuta Alexandru on 6/18/2017.
 */
public class Day {

    private String date;
    private List<Integer> listSteps= new ArrayList<>();

    public Day() {

    }

    public List<Integer> getListSteps() {
        return listSteps;
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
