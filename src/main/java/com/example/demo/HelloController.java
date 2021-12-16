package com.example.demo;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class HelloController {

    @FXML private Text errText;
    @FXML private Button RegisterButton;
    @FXML private Button LoginButton;
    @FXML private TextField BalanceTextField;
    @FXML private TextField username;
    @FXML private Hyperlink SignupLink;
    @FXML private Hyperlink LoginLink;
    @FXML private BorderPane borderPanel;
    @FXML private TextField phoneNumber;
    @FXML private PasswordField passwordField;
    @FXML private AnchorPane anchorPane;

    @FXML private void initialize() {
        anchorPane.requestFocus();
    }

    @FXML private void exit() {
        System.exit(0);
    }


    @FXML private void LoginPressed() {

        if (Objects.equals(phoneNumber.getText(), "")) {
            errText.setText("Not a valid phone number.");
        }

        DbSqlite sql = new DbSqlite();
        try {
            switch (sql.login(Integer.parseInt(phoneNumber.getText()), passwordField.getText())) {
                case "ErrPhoneNum" -> errText.setText("This phone number has not been registered yet.");
                case "ErrWrongPassword" -> errText.setText("Incorrect password.");
                case "Login" -> {
                    errText.setText("");

                    HelloApplication.ActiveUserNumber = Integer.parseInt( phoneNumber.getText() );

                    loginSwitchScene();
                }
                case "empty" -> errText.setText("Not a valid phone number");
            }
        }catch (NumberFormatException e) {
            // makes not a valid phone number input
        }
    }




        @FXML private void RegisterPressed() {

        try {

            User user = new User(
                    Integer.parseInt(phoneNumber.getText()),
                    username.getText(),
                    passwordField.getText(),
                    Integer.parseInt(BalanceTextField.getText()));

            DbSqlite sql = new DbSqlite();


            if (sql.checkUser(user.getPhoneNumber())) {

                 sql = new DbSqlite();

                sql.addUser(user);
                } else {
                errText.setText("A user with this phone number already exists.");
            }

        } catch (NumberFormatException e ) {
            // Make it clear only whole number in balance for now, maybe add a system for 2 decimal comma later
            System.out.println("Balance should only be integers.");
        }

    }

    @FXML private void RegisterLinkPressed() {

        changeFields(true);
    }

    @FXML private  void LoginLinkPressed() {
        changeFields(false);
    }

    private void changeFields(boolean register ){

        LoginButton.setVisible(!register);
        username.setVisible(register);
        BalanceTextField.setVisible(register);
        RegisterButton.setVisible(register);
        SignupLink.setVisible(!register);
        LoginLink.setVisible(register);

        borderPanel.requestFocus();
    }

    public void loginSwitchScene() {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMabye.fxml")));
            Stage window = (Stage) LoginButton.getScene().getWindow();

            window.setScene(new Scene(root , 700, 600));

            window.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}