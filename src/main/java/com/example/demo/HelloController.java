package com.example.demo;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class HelloController {

    @FXML private Button RegisterButton;
    @FXML  private Button LoginButton;
    @FXML private TextField BalanceTextField;
    @FXML private TextField ConfirmPasswordTextField;
    @FXML private Hyperlink SignupLink;
    @FXML private Hyperlink LoginLink;
    @FXML private BorderPane borderPanel;

    @FXML private void LoginPressed() {
        System.out.println("the button has been pressed");

    }

    @FXML private void RegisterPressed() {

    }


    @FXML private void RegisterLinkPressed() {

        changeFields(true);
    }

    @FXML private  void LoginLinkPressed() {
        changeFields(false);
    }

    private void changeFields(boolean register ){

        LoginButton.setVisible(!register);
        ConfirmPasswordTextField.setVisible(register);
        BalanceTextField.setVisible(register);
        RegisterButton.setVisible(register);
        SignupLink.setVisible(!register);
        LoginLink.setVisible(register);

        borderPanel.requestFocus();
    }




}