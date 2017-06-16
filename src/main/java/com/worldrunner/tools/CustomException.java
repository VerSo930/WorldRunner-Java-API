package com.worldrunner.tools;


/**
 * Created by Vuta Alexandru on 6/13/2017.
 */
public class CustomException extends Exception {

    private int code;

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
