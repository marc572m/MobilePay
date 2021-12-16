package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.List;

public class RequestController {


    @FXML
    public void acceptRequest(ActionEvent event) {
        DbSqlite dbSqlite = new DbSqlite();
        Request request = dbSqlite.getRequest(Integer.parseInt(getRequestId(event).substring(1)));
        int amount = request.getAmount();
        int receiverNumber = request.getSenderOfRequests();
        dbSqlite = new DbSqlite();
        User user = dbSqlite.getUser(HelloApplication.ActiveUserNumber);

        Button button = (Button) event.getSource();
        AnchorPane anchorPane = (AnchorPane) button.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();

        if (user.getBalance() < amount) {

            TextFlow border = (TextFlow) anchorPane.lookup("#requestTextBorder");
            Text text = (Text) anchorPane.lookup("#requestText");

            border.setStyle("-fx-border-color:  #ff0000" );
            border.setVisible(true);
            text.setText("Insufficient funds");
            text.setFill(Paint.valueOf("#ff0000"));


        } else {


            Text balanceText = (Text) anchorPane.lookup("#theBalance");

            dbSqlite = new DbSqlite();
            dbSqlite.sendMoney(HelloApplication.ActiveUserNumber, receiverNumber, amount);

            dbSqlite = new DbSqlite();
            user = dbSqlite.getUser(HelloApplication.ActiveUserNumber);
            balanceText.setText("$ " + user.getBalance());

            String requestId = getRequestId(event);
            int reqId = Integer.parseInt(requestId.substring(1));

            dbSqlite = new DbSqlite();
            Request r = dbSqlite.getRequest(reqId);

            processRequest(event);

            dbSqlite = new DbSqlite();

            dbSqlite.createTransaction("requestAccepted" , r.getSenderOfRequests() , HelloApplication.ActiveUserNumber , r.getAmount() );

            TextFlow border = (TextFlow) anchorPane.lookup("#requestTextBorder");
            Text text = (Text) anchorPane.lookup("#requestText");

            border.setStyle("-fx-border-color: rgba(45, 175, 45, 0.85)" );
            border.setVisible(true);
            text.setText("Request accepted");
            text.setFill(Paint.valueOf("rgba(45, 175, 45, 0.85)"));
        }
    }

    @FXML private void declineRequest (ActionEvent event) {

        Button button = (Button) event.getSource();
        AnchorPane anchorPane = (AnchorPane) button.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();

        TextFlow border = (TextFlow) anchorPane.lookup("#requestTextBorder");
        Text text = (Text) anchorPane.lookup("#requestText");

        border.setStyle("-fx-border-color:  #ff0000 " );
        border.setVisible(true);
        text.setText("Request declined");
        text.setFill(Paint.valueOf("#ff0000"));

        String requestId = getRequestId(event);
        int reqId = Integer.parseInt(requestId.substring(1));

        DbSqlite dbSqlite = new DbSqlite();
        Request r =  dbSqlite.getRequest(reqId);

        processRequest(event);

        dbSqlite = new DbSqlite();

        dbSqlite.createTransaction("requestDeclined" , r.getSenderOfRequests() , HelloApplication.ActiveUserNumber , r.getAmount() );


    }

    public String getRequestId(ActionEvent event) {
        Button button = (Button) event.getSource();
        GridPane gridPane = (GridPane) button.getParent();

        List<Node> nodes  = gridPane.getChildren();
        AnchorPane anchorPane = (AnchorPane) nodes.get(2);
        List<Node> nodes1 =  anchorPane.getChildren();
        Text requestIdText = (Text) nodes1.get(3);

        return  requestIdText.getText();



    }

    public GridPane getGridPane(ActionEvent event) {
        Button button = (Button) event.getSource();
        return (GridPane) button.getParent();
    }

    public void processRequest(ActionEvent event) {
        String requestId = getRequestId(event);
        int reqId = Integer.parseInt(requestId.substring(1));

        DbSqlite sqlite = new DbSqlite();
        sqlite.deleteTransaction(reqId);

        Button button = (Button) event.getSource();
        VBox vBox = (VBox) button.getParent().getParent();
        vBox.getChildren().remove( getGridPane(event) );
    }




}
