package database;

import helper.Constant;

import java.sql.*;

public class DataBaseHelper{
    private Connection connection;
    private ResultSet rs;
    private Statement statement;

    public void initConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+ Constant.DB_NAME+".db");
            printDbConnectionStatus(connection);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void initConnection(String dbName){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
            printDbConnectionStatus(connection);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateQuery(String sqlQuery){
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(40);
            statement.executeUpdate(sqlQuery);
            printQueryStatus();
        }catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void printQueryStatus() {
        System.out.println("Data updated successfully!");
    }

    public ResultSet execQuery(String sqlQuery){
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(40);
            rs = statement.executeQuery(sqlQuery);
        }catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

    public void printDbConnectionStatus(Connection connection){
        if (isDbConnected(connection)){
            System.out.println("Db connected successfully");
        }else {
            System.err.println("Db connection failed!!!");
        }
    }

    public boolean isDbConnected(Connection connection) {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException ignored) {}
        return false;
    }

}
