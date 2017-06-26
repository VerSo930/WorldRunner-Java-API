package com.worldrunner.model;

import com.google.gson.Gson;

/**
 * Created by vuta on 09/06/2017.
 */

public class MyResponse<T> {

    private String status;
    private String message;
    private int error;
    private T data;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MyResponse() {}

    public T getData() {
        return data;
    }
    public String  toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);

    }

    public void setData(T data) {
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
