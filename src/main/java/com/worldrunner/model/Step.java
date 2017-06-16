package com.worldrunner.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Step implements Serializable {
    private static final long serialVersionUID = 2L;


    private java.sql.Timestamp lastupdate;
    private java.sql.Timestamp day;
    private ArrayList<Integer> steps;

    public java.sql.Timestamp getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(java.sql.Timestamp lastupdate) {
        this.lastupdate = lastupdate;
    }

    public java.sql.Timestamp getDay() {
        return day;
    }

    public void setDay(java.sql.Timestamp day) {
        this.day = day;
    }

    public ArrayList<Integer> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Integer> steps) {
        this.steps = steps;
    }

    public void addSteps (Integer integer) {
        if(this.steps == null){
            this.steps = new ArrayList<>();
        }
        this.steps.add(integer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Step step = (Step) o;

        if (lastupdate != null ? !lastupdate.equals(step.lastupdate) : step.lastupdate != null) return false;
        if (day != null ? !day.equals(step.day) : step.day != null) return false;
        return steps != null ? steps.equals(step.steps) : step.steps == null;
    }

    @Override
    public int hashCode() {
        int result = lastupdate != null ? lastupdate.hashCode() : 0;
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (steps != null ? steps.hashCode() : 0);
        return result;
    }
}




