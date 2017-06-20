package com.worldrunner.model.step;

import com.worldrunner.model.User;

import java.util.ArrayList;
import java.util.List;

public class Step {

    private int userId;
    private List<Day> dayList;

    public Step() {
        dayList = new ArrayList<>();
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public Day getDay(Day day) {
        Boolean found = false;
        if(dayList != null) {
            for (Day aDayList : dayList) {
                if (day.eq(aDayList)) {
                    day = aDayList;
                    found = true;
                    break;
                }
            }
        }
        if(!found){
            dayList.add(day);
        }

        return day;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}



