package com.worldrunner.tools;

/**
 * Created by Vuta Alexandru on 6/7/2017.
 */
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;

import java.sql.Connection;


public class Database  {

    public static Connection getConnection() throws CustomException
    {
        Connection conn;
        try {

               Context initContext = new InitialContext();
               Context envContext  = (Context)initContext.lookup("java:/comp/env");
               DataSource datasource = (DataSource)envContext.lookup("jdbc/LocalTestDB");
               conn = datasource.getConnection();

           } catch (Exception e) {
             throw new CustomException(e.getMessage(), 500);
           }

           return conn;

    }

    public static void close(Connection connection)
    {
        try {
            // If connection exists, close
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}