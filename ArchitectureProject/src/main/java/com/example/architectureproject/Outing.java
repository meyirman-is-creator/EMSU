package com.example.architectureproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Outing extends Application {
    Stage stage;

    public Outing(Stage currentStage) {
        stage = currentStage;
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();

        ImageView questionIcon = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\question.png"));
        questionIcon.setFitHeight(55.0);
        questionIcon.setFitWidth(55.0);
        questionIcon.setLayoutX(24.0);
        questionIcon.setLayoutY(25.0);
        questionIcon.setOpacity(0.58);

        Button yesButton = new Button("YES");
        yesButton.setLayoutX(88.0);
        yesButton.setLayoutY(131.0);

        Button noButton = new Button("NO");
        noButton.setLayoutX(272.0);
        noButton.setLayoutY(131.0);
        yesButton.prefHeight(40);
        yesButton.prefWidth(40);
        noButton.prefHeight(40);
        noButton.prefWidth(40);
        Text questionText = new Text("Do you want to log out?");
        questionText.setLayoutX(88.0);
        questionText.setLayoutY(59.0);
        questionText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        questionText.setStrokeWidth(0.0);
        questionText.setWrappingWidth(284.64208984375);
        questionText.setFont(new Font(18.0));

        yesButton.setOnAction(event -> {
            stage.close();
            Stage currentStage = (Stage) yesButton.getScene().getWindow();
            SignInSignUpApp signInSignUpApp = new SignInSignUpApp(currentStage);

            Stage signinStage = new Stage();
            signInSignUpApp.start(signinStage);
            closeCurrentPanel(primaryStage);
        });
        noButton.setOnAction(event -> closeCurrentPanel(primaryStage));

        root.getChildren().addAll(questionIcon, yesButton, noButton, questionText);

        Scene scene = new Scene(root, 400, 184);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Logout Confirmation Dialog");
        primaryStage.show();
    }



    private void closeCurrentPanel(Stage currentStage) {
        currentStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
