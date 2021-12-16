package com.example.demo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class MainSceneController {


    // SideBarButtons
    @FXML private Button sideBarMakePayment;
    @FXML private Button sideBarRequestMoney;

    @FXML private Button transactionsMenuButton;
    @FXML private Button requestMenuButton;

    // Application showing
    @FXML private AnchorPane SendMenu;
    @FXML private AnchorPane requestMenu;

    // The blackBlue Bar tingy
    @FXML private VBox vbox;
    @FXML private VBox transactionsVBox;

    // Send Money Controllers
    @FXML private Text userName;
    @FXML private Text theBalance;
    @FXML private TextField sendAmount;
    @FXML private TextField sendNumber;
    @FXML private Text errText;
    @FXML private TextFlow errTextBorder;
    @FXML private TextFlow  successTextBox;
    @FXML private Text successSendMessage;

    //Request Money Controllers

    public void initialize() {
            DbSqlite  sql = new DbSqlite();
            User user =  sql.getUser(HelloApplication.ActiveUserNumber);

            userName.setText(user.getUsername());
            theBalance.setText("$ " + user.getBalance());

        makePaymentMenu();

        transactionsMenu();

      //  TranslateTransition t = new TranslateTransition(Duration.seconds(1)  , vbox );

        handleRequest();

        handleTransactions();
    }

    public void transactionsMenu() {
        transactionsVBox.setVisible(true);
        vbox.setVisible(false);
        vbox.managedProperty().bind(vbox.visibleProperty());
        transactionsVBox.managedProperty().bind(transactionsVBox.visibleProperty());

        transactionsMenuButton.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: #135a83; -fx-text-fill: #135a83 ");
        requestMenuButton.setStyle("");

    }

    public void requestMenu() {
        vbox.setVisible(true);
        transactionsVBox.setVisible(false);
        vbox.managedProperty().bind(vbox.visibleProperty());
        transactionsVBox.managedProperty().bind(transactionsVBox.visibleProperty());

        requestMenuButton.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: #135a83; -fx-text-fill: #135a83 ");
        transactionsMenuButton.setStyle("");

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

                    sql = new DbSqlite();

                    sql.createTransaction("send" , HelloApplication.ActiveUserNumber , phoneNumberToReceiveMoney , paymentAmount );


                } else {
                    errText.setText("Insufficient funds");
                    errTextBorder.setVisible(true);
                    sendAmount.requestFocus();

                }

            } catch (NumberFormatException e) {
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

            if (sql.checkUser(receiverOfRequests)) {
                errText.setText("This number is invalid.");
                errTextBorder.setVisible(true);
                successSendMessage.setText("");
                sendNumber.requestFocus();
            } else {


                sql = new DbSqlite();

                sql.createRequest(senderOfRequests, receiverOfRequests, paymentAmount);

                successTextBox.setVisible(true);

                sql = new DbSqlite();

                User user = sql.getUser(receiverOfRequests);

                successSendMessage.setText("A request of $" + sendAmount.getText() + " has been send to " + user.getUsername());

                sendAmount.setText("");
                sendNumber.setText("");



            }
            } catch(NumberFormatException e ){

                errText.setText("This number is invalid.");
                errTextBorder.setVisible(true);
                successSendMessage.setText("");
            }

    }

    public void handleRequest() {

        DbSqlite  sql = new DbSqlite();

        ArrayList<Request> yourRequests = sql.returnYourRequest();

        try {
            System.out.println(yourRequests.size()  );

            for (Request yourRequest : yourRequests) {

                GridPane gridPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Requests.fxml")));
                vbox.getChildren().add(gridPane);

                Text requestText = (Text) gridPane.lookup("#requestText");
                Text requestMoneyAmount = (Text) gridPane.lookup("#requestMoneyAmount");
                Text requestDate = (Text) gridPane.lookup("#requestDate");
                Text requestId = (Text) gridPane.lookup("#requestId");

                sql = new DbSqlite();

                User user = sql.getUser(yourRequest.getSenderOfRequests());

                requestText.setText(user.getUsername() + " send you a request for");
                requestMoneyAmount.setText("$ " + yourRequest.getAmount());
                requestDate.setText(yourRequest.getDateTime());
                requestId.setText("#" + yourRequest.getTransactionId());


            }

        }   catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleTransactions() {
        DbSqlite sqlite = new DbSqlite();

        ArrayList<Transaction> transactions  =  sqlite.returnYourTransactions();

        transactions.sort(Transaction.transitionid);


        try {
            System.out.println(transactions.size());

        for (Transaction transaction : transactions) {

            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("transactions.fxml")));
            transactionsVBox.getChildren().add(anchorPane);

            Text transactionText = (Text) anchorPane.lookup("#text");
            Text transactionAmount = (Text) anchorPane.lookup("#amount");
            Text transactionData = (Text) anchorPane.lookup("#date");
            Text transactionId = (Text) anchorPane.lookup("#id");

            DbSqlite dbSqlite = new DbSqlite();
            User user =  dbSqlite.getUser(transaction.getReceiverNumber());

            if (transaction.getSenderNumber() == HelloApplication.ActiveUserNumber ) {

                switch (transaction.getTransactionType()) {
                    case "send" -> {
                        transactionText.setText("You send " + user.getUsername());
                        transactionAmount.setFill(Paint.valueOf("#FF0000FF"));
                        transactionAmount.setText("$ -" + transaction.getAmount());
                    }
                    case "requestAccepted" -> {
                        transactionText.setText(user.getUsername() + " accepted your request for");
                        transactionAmount.setFill(Paint.valueOf("#27d027"));
                        transactionAmount.setText("$ " + transaction.getAmount());
                    }
                    case "requestDeclined" -> {
                        transactionText.setText(user.getUsername() + "s' declined your request for");
                        transactionAmount.setFill(Paint.valueOf("#5b5b5b"));
                        transactionAmount.setText("$ " + transaction.getAmount());
                    }
                }


            } else {

                switch (transaction.getTransactionType()) {
                    case "send" -> {
                        transactionText.setText(user.getUsername() + " send you ");
                        transactionAmount.setFill(Paint.valueOf("#27d027"));
                        transactionAmount.setText("$ " + transaction.getAmount());
                    }
                    case "requestAccepted" -> {
                        transactionText.setText("You accepted " + user.getUsername() + "'s request for");
                        transactionAmount.setFill(Paint.valueOf("#FF0000FF"));
                        transactionAmount.setText("$ -" + transaction.getAmount());
                    }
                    case "requestDeclined" -> {
                        transactionText.setText("You declined " + user.getUsername() + "'s request for");
                        transactionAmount.setFill(Paint.valueOf("#5b5b5b"));
                        transactionAmount.setText("$ " + transaction.getAmount());
                    }
                }

            }

            transactionData.setText(  transaction.getDate().toString() );
            transactionId.setText("#" +  transaction.getTransitionId());

        }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    public void signOut() {

        HelloApplication.ActiveUserNumber = 0;

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Stage window = (Stage) vbox.getScene().getWindow();

            window.setScene(new Scene(root , 700, 600));

            window.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }





    }
    public void exit() {

        System.exit(0);


    }


}



