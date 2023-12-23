package com.example.architectureproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/demo6/calendar1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Teacher's calendar!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {

        launch();
    }
}