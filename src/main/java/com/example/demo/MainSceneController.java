package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class MainSceneController {

    @FXML private Text userName;
    @FXML private Text theBalance;
    @FXML private TextField sendAmount;
    @FXML private TextField sendNumber;
    @FXML private Text errText;
    @FXML private Text numberName;



    public void initialize() {
            DbSqlite  sql = new DbSqlite();
            User user =  sql.getUser(HelloApplication.ActiveUserNumber);

            userName.setText("Name: " + user.getUsername());
            theBalance.setText("$ " + user.getBalance());
    }


    public void makePayment () {

        int paymentAmount;
        int phoneNumberToReceiveMoney;


        try {
            paymentAmount =  Integer.parseInt(sendAmount.getText());
            phoneNumberToReceiveMoney = Integer.parseInt(sendNumber.getText());


        DbSqlite sql = new DbSqlite();
        DbSqlite sql3 = new DbSqlite();


          if (sql.checkUser(phoneNumberToReceiveMoney)) {
            errText.setText("This number is invalid.");
            sendNumber.requestFocus();


        } else if ( paymentAmount < sql3.returnBalance(  HelloApplication.ActiveUserNumber ) )  {

            DbSqlite dbSqlite = new DbSqlite();
            dbSqlite.sendMoney( HelloApplication.ActiveUserNumber ,  phoneNumberToReceiveMoney , paymentAmount  );

            DbSqlite sql2 = new DbSqlite();
            User user =  sql2.getUser(HelloApplication.ActiveUserNumber);
            theBalance.setText("$ " + user.getBalance());
            sendAmount.setText("");
            sendNumber.setText("");
            errText.setText("");


        } else {
            errText.setText ("Insufficient funds");
            sendAmount.requestFocus();

    }

    } catch (NumberFormatException e ) {
        // make the text say its not a valid number
    }

    }




}
