package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;




public class HelloApplication extends Application {


    public static int ActiveUserNumber;// du en ged

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700 , 600);
        stage.setTitle("MarcPay");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);



        stage.show();
        scene.lookup("BorderPane").requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }





}


