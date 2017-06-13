package com.worldrunner.tools;

/**
 * Created by Vuta Alexandru on 6/7/2017.
 */
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    // TODO: Implement pool connection for mysql
    public static Connection getConnection() throws Exception
    {
        Connection conn = null;
        try {
            // Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://vuta-alexandru.com/spring_test", "root", "vuta91929394");
            System.out.println("MYSQL =>> Connected database successfully...");
            return conn;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return null;
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