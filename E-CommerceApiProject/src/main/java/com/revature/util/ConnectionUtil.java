package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    private static Connection conn = null;

    private ConnectionUtil (){

    }

    public static Connection getConnection (){

        try {
            if (conn != null && !conn.isClosed()){
                // It means that the connection exists
                return  conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        //Otherwise we create a new connection

        String url;
        String username;
        String password;

        Properties prop = new Properties();

        try {
            prop.load(new FileReader("src/main/resources/application.properties"));
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");

            conn = DriverManager.getConnection(url,username,password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.out.println("Could not establish connection");
        }


        return conn;
    }

}
