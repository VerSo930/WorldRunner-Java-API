package com.worldrunner.model;

/**
 * Created by vuta on 09/06/2017.
 */
public class MyResponse<T> {

    private String status;
    private String message;
    private String error;
    private T obj;
    private String typage;
    private int code;


    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public MyResponse() {
        error = null;
        this.code = 200;
    }



    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
        if(obj != null) {
            typage = obj.getClass().getSimpleName();
        }
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

    public String getTypage() {
        return typage;
    }

    public void setTypage(String typage) {
        this.typage = typage;
    }

    public boolean checkUser(){

        User user = (User) obj;
        if (user == null || !user.checkValues() ) {
                setStatus("failed");
                setMessage("You need to fill all params!");
                setError("Parameters not provided");
                setCode(400);
                return false;
        }

       return true;
    }
}
