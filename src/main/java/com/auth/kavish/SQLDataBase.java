package com.auth.kavish;

import com.auth.kavish.model.Profile;

import java.sql.*;

public class SQLDataBase {

    public boolean addUser(Profile user) throws ClassNotFoundException {
        return connect("Add", user);
    }

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    private boolean connect (String operation, Profile user) throws ClassNotFoundException {
        String connectionUrl =
                "jdbc:sqlserver://userdataforloginlogout.database.windows.net:1433;database=userData;user=userData@userdataforloginlogout;password=LoginLogout@1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        // load and register JDBC driver for MySQL
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        try (Connection connection = DriverManager.getConnection(connectionUrl);){

            if (operation == "Add")
            {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO userData values (?,?,?)");
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.executeUpdate();
                ps.close();
            }
            else
            {
                return false;
            }

            return true;
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
