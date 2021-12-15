package com.example.demo;

import javafx.animation.TranslateTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Objects;


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
    @FXML private TextFlow errTextBorder;
    @FXML private TextFlow  successTextBox;
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


        successTextBox.setVisible(false);
        SendMenu.setVisible(true);
        requestMenu.setVisible(false);

    }
    public void requestMoneyMenu() {
        sideBarRequestMoney.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: #135a83; -fx-text-fill: #135a83 ");
        sideBarMakePayment.setStyle("");


        successTextBox.setVisible(false);
        SendMenu.setVisible(false);
        requestMenu.setVisible(true);

    }

    public void makePayment () {

        successTextBox.setVisible(false);

        int paymentAmount;
        int phoneNumberToReceiveMoney;

        if (Objects.equals(sendAmount.getText(), "")) {
            errText.setText("Empty field");
            errTextBorder.setVisible(true);
            sendAmount.requestFocus();
        } else if (Objects.equals((sendNumber.getText()) , "")) {
            errText.setText("Empty field");
            errTextBorder.setVisible(true);
            sendNumber.requestFocus();
        } else {

            try {
                paymentAmount = Integer.parseInt(sendAmount.getText());
                phoneNumberToReceiveMoney = Integer.parseInt(sendNumber.getText());


                DbSqlite sql = new DbSqlite();
                int Balance = sql.returnBalance(HelloApplication.ActiveUserNumber);
                sql = new DbSqlite();


                if (sql.checkUser(phoneNumberToReceiveMoney)) {
                    errText.setText("This number is invalid.");
                    errTextBorder.setVisible(true);
                    successSendMessage.setText("");
                    sendNumber.requestFocus();


                } else if (paymentAmount < Balance) {

                    sql = new DbSqlite();
                    sql.sendMoney(HelloApplication.ActiveUserNumber, phoneNumberToReceiveMoney, paymentAmount);

                    sql = new DbSqlite();
                    User user = sql.getUser(HelloApplication.ActiveUserNumber);
                    theBalance.setText("$ " + user.getBalance());
                    sendAmount.setText("");
                    sendNumber.setText("");
                    errText.setText("");
                    errTextBorder.setVisible(false);

                    sql = new DbSqlite();

                    user = sql.getUser(phoneNumberToReceiveMoney);


                    successSendMessage.setText("Successfully send $" + paymentAmount + " to " + user.getUsername());
                    successTextBox.setVisible(true);

                } else {
                    errText.setText("Insufficient funds");
                    errTextBorder.setVisible(true);
                    sendAmount.requestFocus();

                }

            } catch (NumberFormatException e) {
                // make the text say its not a valid number
                errText.setText("Not a valid number");
            }

        }
    }

    public void makeRequest() {
        successTextBox.setVisible(false);
        try {

            int paymentAmount = Integer.parseInt(sendAmount.getText());
            int senderOfRequests = HelloApplication.ActiveUserNumber;
            int receiverOfRequests = Integer.parseInt(sendNumber.getText());

            // Test if the input amount is a read money number

            // check if the phone number is real and registered in the database






            DbSqlite sql = new DbSqlite();
            sql.createRequest(senderOfRequests, receiverOfRequests, paymentAmount);

                successTextBox.setVisible(true);

                sql = new DbSqlite();

                User user = sql.getUser(receiverOfRequests);

                successSendMessage.setText("A request of $" + sendAmount.getText() + " has been send to " + user.getUsername());

            }
            } catch(NumberFormatException e ){

                errText.setText("This number is invalid.");
                errTextBorder.setVisible(true);
                successSendMessage.setText("");
            }

    }

    public void handleRequest() {

        DbSqlite  sql = new DbSqlite();

        ArrayList<Request> yourRequests = sql.returnYourRequest(HelloApplication.ActiveUserNumber);

        System.out.println(yourRequests.size());

        try {

            for (int i = 0; i < yourRequests.size(); i++) {

                GridPane gridPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Requests.fxml")));
                vbox.getChildren().add(gridPane);

                Text requestText = (Text) gridPane.lookup("#requestText");
                Text requestMoneyAmount = (Text) gridPane.lookup("#requestMoneyAmount");
                Text requestDate = (Text) gridPane.lookup("#requestDate");
                Text requestId = (Text) gridPane.lookup("#requestId");

                sql = new DbSqlite();

                User user =  sql.getUser(yourRequests.get(i).getSenderOfRequests() );

                requestText.setText(  user.getUsername()  +   " send you a request for");
                requestMoneyAmount.setText("$ " + yourRequests.get(i).getAmount());
                requestDate.setText( yourRequests.get(i).getDateTime() );
                requestId.setText("#" + yourRequests.get(i).getTransactionId());


            }

        }   catch (IOException e) {
            e.printStackTrace();
        }

    }









}
