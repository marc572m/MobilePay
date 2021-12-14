package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;

public class requestController {


    @FXML
    public void acceptRequest(ActionEvent event) {
        String requestId = getRequestId(event);


        // we now the id of the request and can use it to get the date we need to make the transaction

        System.out.println(requestId);

    }

    @FXML private void declineRequest (ActionEvent event) {
        String requestId = getRequestId(event);

        // we now the id of the request and can use it to get the date we need to delete the request and move it to another databse


        System.out.println(requestId);
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


}
