package edu.aitu.oop3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:postgresql://aws-1-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER = "postgres.vuvepkcvnbvlxhuqxbjw";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database connection", e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }
}
