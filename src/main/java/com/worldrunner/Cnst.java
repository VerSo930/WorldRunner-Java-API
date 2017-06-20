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
    public static final String MSG_DELETE_USER = "user deleted successfully";

    // Status codes
    public static final int C_BAD_REQUEST = 400;
    public static final int C_NOT_FOUND = 404;
    public static final int C_NOT_ALLOWED = 401;
    public static final int C_REQ_OK = 200;
    public static final int C_CREATED = 201;
    public static final int C_ERROR = 500;

    // User Mysql Query's
    public static final String SQL_INSERT_USER = "INSERT INTO user (firstName, lastName, email, password, country, weight, height) VALUE (?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_UPDATE_USER = "UPDATE user SET firstName = ?, lastName = ?, email = ?, password = ?, country = ?, weight = ?, height = ? WHERE id = ?";
    public static final String SQL_FIND_USER = "SELECT * FROM user WHERE id =?";
    public static final String SQL_FINDALL_USER = "SELECT * FROM user";
    public static final String SQL_DELETE_USER = "DELETE FROM user WHERE id = ?";

    // Step Mysql Query's SELECT * FROM `step` WHERE hour >= CURDATE();
    public static final String SQL_FIND_BY_ID_STEPS = "SELECT * FROM step WHERE userid = ?";
    private static final int FIND_ALL_STEPS_DAYS = 1;
    public static final String SQL_FINDALL_STEP = " SELECT id, hour, steps, userId" +
            " FROM step t" +
            " WHERE DATE(hour) > DATE( DATE_SUB( (SELECT MAX(hour) FROM step where userId = t.userId), INTERVAL "+ FIND_ALL_STEPS_DAYS +" DAY) )" +
            " order by hour, userId desc";


    // User conditions Settings
    public static final int FIRST_NAME_MAX = 50;
    public static final int FIRST_NAME_MIN = 1;
    public static final int LAST_NAME_MAX = 50;
    public static final int LAST_NAME_MIN = 1;
    public static final int EMAIL_MAX = 250;
    public static final int EMAIL_MIN = 3;
    public static final int PASSWORD_MAX = 20;
    public static final int PASSWORD_MIN = 6;




}
