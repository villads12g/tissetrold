package com.example.ProjectGroupApps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class MyDB {
    Connection conn = null;

    MyDB() {
        if (conn == null) open();
    }

    public void open() {
        try {
            String url = "jdbc:sqlite:identifier.sqlite";
            conn = DriverManager.getConnection(url);
            
            // Enable foreign keys in SQLite
            Statement stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.close();
        } catch (SQLException e) {
            System.out.println("cannot open");
            if (conn != null) close();
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        conn = null;
    }

    public void cmd(String sql) {
        if (conn == null) open();
        if (conn == null) {
            System.out.println("No connection");
            return;
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error in statement " + sql);
            throw new RuntimeException(e);
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in statement " + sql);
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> query(String query, String fld) {
        ArrayList<String> res = new ArrayList<>();
        if (conn == null) open();
        if (conn == null) {
            System.out.println("No connection");
            throw new RuntimeException("No connection");
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString(fld);
                res.add(name);
            }
        } catch (SQLException e) {
            System.out.println("Error in statement " + query + " " + fld);
            throw new RuntimeException(e);
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in statement " + query + " " + fld);
            throw new RuntimeException(e);
        }
        return res;
    }
}
