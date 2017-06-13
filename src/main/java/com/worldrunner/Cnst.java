package com.worldrunner;

/**
 * Created by vuta on 09/06/2017.
 */
public class Cnst {

    // ENDPOINTS url's
    public static final String API_URL = "/v1";
    public static final String CONTENT_TYPE = "application/json";
    public static final String ENDPOINT_USERS = "/users";
    public static final String ENDPOINT_STEPS = "/steps";

    // Fail success status
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    // Endpoints return message
    public static final String MSG_FIND_ALL = "find all users <List>";
    public static final String MSG_FIND_BY_ID = "find user by id";
    public static final String MSG_INSERT_USER = "insert new user";
    public static final String MSG_UPDATE_USER = "update user";

    // Status codes
    public static final int C_BAD_REQUEST = 400;
    public static final int C_NOT_FOUND = 404;
    public static final int C_NOT_ALLOWED = 401;
    public static final int C_REQ_OK = 200;
    public static final int C_CREATED = 201;
    public static final int C_ERROR = 500;


}
