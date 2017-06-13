package com.worldrunner.model;

import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vuta on 09/06/2017.
 */

public class MyResponse<T> {

    private String status;
    private String message;
    private String error;
    private T obj;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MyResponse() {}

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }



}
