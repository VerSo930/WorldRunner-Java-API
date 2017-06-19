package com.worldrunner.model.step;

import com.worldrunner.model.User;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vuta Alexandru on 6/18/2017.
 */
public class MyResponseStep {


    private String status;
    private String message;
    private int error;
    private  HashMap<User, HashMap<Integer, List<Integer>>> data;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MyResponseStep() {}

    public  HashMap<User, HashMap<Integer, List<Integer>>> getData() {
        return data;
    }

    public void setData( HashMap<User, HashMap<Integer, List<Integer>>> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }


}
