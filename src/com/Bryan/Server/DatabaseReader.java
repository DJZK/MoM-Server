package com.Bryan.Server;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseReader extends Main.netServer {
    public static String databasePath = "C:\\Database\\Server.db";
    public static String DatabaseParameter = "(Username, Password, Type)";
    public static int clientCounter;
    public static String inName, inPass, inReg, inType;

    DatabaseReader(Socket socket) throws IOException {
        super(socket);
    }


    public static void checkAccount(String username) {
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath); Statement statement = conn.createStatement()) {
                statement.execute("SELECT * FROM Accounts WHERE Username = '" + username + "'");
                try (ResultSet results = statement.getResultSet()) {
                    while (results.next()) {

                        User.setUsername(results.getString("Username"));
                        User.setPassword(results.getString("Password"));
                    }

                }

            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + e.getMessage());
        }
    }

    public static void loadUser(String username) {
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath); Statement statement = conn.createStatement()) {
                statement.execute("SELECT * FROM Accounts WHERE Username = '" + username + "'");
                try (ResultSet results = statement.getResultSet()) {
                    while (results.next()) {
                        User.setUsername(results.getString("Username"));
                        User.setPassword(results.getString("Password"));
                        User.setRegion(results.getString("Region"));
                        User.setType(results.getString("Type"));
                    }

                }

            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + e.getMessage());
        }

    }

    public static void genReport(String Region) {
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath); Statement statement = conn.createStatement()) {
                statement.execute("SELECT * FROM Report WHERE Region = '" + Region + "'");
                try (ResultSet results = statement.getResultSet()) {
                    clientCounter = 0;
                    while (results.next()) {
                        var shout = new PrintWriter(socket.getOutputStream(), true);
                        //  print(results.getString("Email") + " ");
                        // println(results.getString("ClientName"));
                        shout.println((results.getString("Email") + " ") + results.getString("ClientName"));
                        //   shout.print(results.getString("ClientName"));
                        clientCounter++;
                    }
                }

            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void print(String message) {
        System.out.print(message);

    }
}
