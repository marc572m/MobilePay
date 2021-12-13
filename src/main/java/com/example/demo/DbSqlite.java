package com.example.demo;

import java.sql.* ;
import java.util.Objects;


public class DbSqlite {

    private Connection connection;

    DbSqlite() {
        connection = null;


        try {
            String url =
 "jdbc:sqlite:src/main/resources/Sqlite/MarcPay.db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    public boolean checkUser(int phoneNumber) {

        String sql = "SELECT * FROM Users WHERE phoneNumber = " + phoneNumber;

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {

                int sqlPhoneNumber =  resultSet.getInt(1);

                if (  sqlPhoneNumber == phoneNumber ) {
                    connection.close();
                    return false;
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();

        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }




    public void addUser(User user) {

        String sql = "INSERT INTO Users (phoneNumber , username ,password , balance) VALUES " +
                "('" + user.getPhoneNumber() + "','" + user.getUsername() + "','" +
                user.getPassword() + "','" + user.getBalance() + "' )" ;

        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);


            connection.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String login ( Integer phoneNumber, String password  ) {

        DbSqlite dbSqlite = new DbSqlite();

        if (dbSqlite.checkUser(phoneNumber)) {
            //phone number does not exist
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return "ErrPhoneNum";
        }

        String sql = "SELECT * FROM Users WHERE phoneNumber = " + phoneNumber;
        Statement statement = null;
        try {
            Statement statement = connection.createStatement();


        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
           if (Objects.equals(resultSet.getString(3), password)){

               connection.close();

               return "Login";
           } else {
               connection.close();
               return "ErrWrongPassword";
           }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "Err";
    }


}
