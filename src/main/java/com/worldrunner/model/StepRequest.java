package com.worldrunner.model;

/**
 * Created by vuta on 16/06/2017.
 */
public class StepRequest {

    public int userId;
    public String dateTime;

    public int limit;
    public int pageNumber;

    public StepRequest() {
        this.limit = 20;
        this.pageNumber = 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
