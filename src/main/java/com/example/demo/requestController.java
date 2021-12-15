package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class requestController {


    @FXML
    public void acceptRequest(ActionEvent event) {
        DbSqlite dbSqlite = new DbSqlite();
        Request request = dbSqlite.getRequest(Integer.parseInt(getRequestId(event)));
        int amount = request.getAmount();
        int receiverNumber = request.getSenderOfRequests();
        dbSqlite = new DbSqlite();
        User user = dbSqlite.getUser(HelloApplication.ActiveUserNumber);

        Button button = (Button) event.getSource();
        AnchorPane anchorPane = (AnchorPane) button.getParent().getParent().getParent().getParent().getParent().getParent().getParent();

        if (user.getBalance() < amount) {



        } else {


            Text text = (Text) anchorPane.lookup("theBalance");

            dbSqlite = new DbSqlite();
            dbSqlite.sendMoney(HelloApplication.ActiveUserNumber, receiverNumber, amount);

            dbSqlite = new DbSqlite();
            user = dbSqlite.getUser(HelloApplication.ActiveUserNumber);
            text.setText("$ " + user.getBalance());

            processRequest(event);
        }
    }

    @FXML private void declineRequest (ActionEvent event) {


        processRequest(event);


    }




    public String getRequestId(ActionEvent event) {

        Button button = (Button) event.getSource();
        GridPane gridPane = (GridPane) button.getParent();

        List<Node> nodes  = gridPane.getChildren();
        AnchorPane anchorPane = (AnchorPane) nodes.get(2);
        List<Node> nodes1 =  anchorPane.getChildren();
        Text requestIdText = (Text) nodes1.get(3);

        return requestIdText.getText();

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
