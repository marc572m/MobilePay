package com.example.demo;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class MainSceneController {

    // SideBarButtons
    @FXML private Button sideBarMakePayment;
    @FXML private Button sideBarRequestMoney;

    // Application showing
    @FXML private AnchorPane SendMenu;
    @FXML private AnchorPane requestMenu;

    // The blackBlue Bar tingy
    @FXML private VBox vbox;

    // Send Money Controllers
    @FXML private Text userName;
    @FXML private Text theBalance;
    @FXML private TextField sendAmount;
    @FXML private TextField sendNumber;
    @FXML private Text errText;
    @FXML private Text numberName;
    @FXML private Text successSendMessage;

    //Request Money Controllers







    public void initialize() {
            DbSqlite  sql = new DbSqlite();
            User user =  sql.getUser(HelloApplication.ActiveUserNumber);

            userName.setText(user.getUsername());
            theBalance.setText("$ " + user.getBalance());

        makePaymentMenu();

        TranslateTransition t = new TranslateTransition(Duration.seconds(1)  , vbox );

        handleRequest();


    }

    public void makePaymentMenu() {
        sideBarMakePayment.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: #135a83; -fx-text-fill: #135a83 ");
        sideBarRequestMoney.setStyle("");



        SendMenu.setVisible(true);
        requestMenu.setVisible(false);

    }
    public void requestMoneyMenu() {
        sideBarRequestMoney.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: #135a83; -fx-text-fill: #135a83 ");
        sideBarMakePayment.setStyle("");



        SendMenu.setVisible(false);
        requestMenu.setVisible(true);

    }

    public void makePayment () {

        int paymentAmount;
        int phoneNumberToReceiveMoney;


        try {
            paymentAmount =  Integer.parseInt(sendAmount.getText());
            phoneNumberToReceiveMoney = Integer.parseInt(sendNumber.getText());


        DbSqlite sql = new DbSqlite();
        int Balance = sql.returnBalance(  HelloApplication.ActiveUserNumber );
         sql = new DbSqlite();


          if (sql.checkUser(phoneNumberToReceiveMoney)) {
            errText.setText("This number is invalid.");
            successSendMessage.setText("");
            sendNumber.requestFocus();


        } else if ( paymentAmount <  Balance )  {

              sql = new DbSqlite();
              sql.sendMoney( HelloApplication.ActiveUserNumber ,  phoneNumberToReceiveMoney , paymentAmount  );

             sql = new DbSqlite();
            User user =  sql.getUser(HelloApplication.ActiveUserNumber);
            theBalance.setText("$ " + user.getBalance());
            sendAmount.setText("");
            sendNumber.setText("");
            errText.setText("");

            sql = new DbSqlite();

            user = sql.getUser(phoneNumberToReceiveMoney);

            successSendMessage.setText("Successfully send $" + paymentAmount + " to " + user.getUsername() );


        } else {
            errText.setText ("Insufficient funds");
            sendAmount.requestFocus();

    }

    } catch (NumberFormatException e ) {
        // make the text say its not a valid number
    }

    }

    public void makeRequest() {

        try {

            int paymentAmount = Integer.parseInt(sendAmount.getText());
            int senderOfRequests = HelloApplication.ActiveUserNumber;
            int receiverOfRequests = Integer.parseInt(sendNumber.getText());

            DbSqlite sql = new DbSqlite();
            sql.createRequest(senderOfRequests, receiverOfRequests, paymentAmount);


        } catch (NumberFormatException e ) {

            // print error
        }

    }

    public void handleRequest() {

        DbSqlite  sql = new DbSqlite();

        ArrayList<Request> yourRequests = sql.returnYourRequest(HelloApplication.ActiveUserNumber);

        System.out.println(yourRequests.size());




    }

    public void acceptRequest() {




    }


}
