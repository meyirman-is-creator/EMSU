package com.example.architectureproject;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationApp extends Application {
    String first_name="";
    Connection connection = connection("designpatterns","designpatterns","Prvtmr1Kkvshdl2.");


    String last_name="";
    int id=Integer.MIN_VALUE;
    String password="";
    String age="";
    String gender="";
    String status="";
    public RegistrationApp(Stage currentStage) {
    }

    public RegistrationApp() {
    }


    public static void main(String[] args) {
        launch(args);
    }

    public Connection connection(String dbName, String user, String password){
        Connection connection  =null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbName,user, password);
            if(connection !=null) System.out.println("connected");
            else System.out.println("disconnect");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return connection;
    }


    @Override
    public void start(Stage primaryStage) {


        AnchorPane root = new AnchorPane();
        root.setPrefSize(1029, 675);

        ImageView background = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\MainPhoto.jpeg"));
        background.setFitWidth(1038);
        background.setFitHeight(702);

        AnchorPane registrationPane = new AnchorPane();
        registrationPane.setLayoutX(275);
        registrationPane.setLayoutY(81);
        registrationPane.setOpacity(0.89);
        registrationPane.setPrefSize(527, 514);
        registrationPane.setStyle("-fx-background-color: white;");

        Label signUpLabel = new Label("Sign Up");
        signUpLabel.setLayoutX(197);
        signUpLabel.setLayoutY(14);
        signUpLabel.setPrefSize(134, 51);
        signUpLabel.setFont(Font.font("System Bold", 35));

        TextField firstNameField = new TextField();
        setupTextField(firstNameField, "First Name", 126, 75);
        TextField lastNameField = new TextField();
        setupTextField(lastNameField, "Last Name", 125, 125);
        TextField idNumberField = new TextField();
        setupTextField(idNumberField, "ID Number", 125, 175);
        PasswordField passwordField = new PasswordField();
        setupPasswordField(passwordField, "Password", 124, 225);
        PasswordField confirmPasswordField = new PasswordField();
        setupPasswordField(confirmPasswordField, "Password Again", 125, 275);

        TextField ageField = new TextField();
        setupTextField(ageField, "Your age", 125, 325);
        ToggleGroup genderToggleGroup = new ToggleGroup();

        RadioButton maleRadioButton = new RadioButton("Male");
        setupRadioButton(maleRadioButton, genderToggleGroup, 125, 375);

        RadioButton femaleRadioButton = new RadioButton("Female");
        setupRadioButton(femaleRadioButton, genderToggleGroup, 310, 375);

        ToggleGroup typeToggleGroup = new ToggleGroup();

        RadioButton studentRadioButton = new RadioButton("Student");
        setupRadioButton(studentRadioButton, typeToggleGroup, 125, 410);

        RadioButton teacherRadioButton = new RadioButton("Teacher");
        setupRadioButton(teacherRadioButton, typeToggleGroup, 310, 410);

        Button signInButton = new Button("Sign Up");
        signInButton.setLayoutX(125);
        signInButton.setLayoutY(455);
        signInButton.setPrefSize(134, 45);
        signInButton.setStyle("-fx-background-radius: 25; -fx-border-image-width: 0; -fx-background-color:  #1e1e2c;");
        signInButton.setTextFill(javafx.scene.paint.Color.web("#fff"));
        signInButton.setFont(Font.font("System Bold", 20));
        signInButton.setCursor(Cursor.HAND);

        Button exit = new Button("Sign In");
        exit.setLayoutX(280);
        exit.setLayoutY(455);
        exit.setPrefSize(114, 45);
        exit.setStyle("-fx-background-radius: 25; -fx-border-image-width: 0;");
        exit.setTextFill(javafx.scene.paint.Color.web("#5e5e5e"));
        exit.setFont(Font.font("Bold", 15));
        exit.setCursor(Cursor.HAND);


        signInButton.setOnAction(event -> {
            SQLDatabaseService dbService = new SQLDatabaseService();
            SQLAdapter adapter = new SQLDatabaseAdapter(dbService);
            Connection connection = adapter.getConnection();
            try {
                if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty() && !idNumberField.getText().isEmpty() &&
                        passwordField.getText().length()<=20 && passwordField.getText().length()>=8 && passwordField.getText().equals(confirmPasswordField.getText()) && !ageField.getText().isEmpty()) {

                    this.first_name = firstNameField.getText();
                    this.last_name = lastNameField.getText();
                    this.id = Integer.parseInt(idNumberField.getText());
                    this.password = passwordField.getText();
                    this.age = ageField.getText();
                    if (maleRadioButton.isSelected()) {
                        this.gender = "male";
                    } else if (femaleRadioButton.isSelected()) {
                        this.gender = "female";
                    }
                    if (studentRadioButton.isSelected()) {
                        this.status = "student";
                    } else if (teacherRadioButton.isSelected())
                        this.status = "teacher";
                    String query_message = String.format("INSERT INTO %s (first_name, last_name, password, id, gender, age) VALUES (?, ?, ?, ?, ?, ?)",this.status+"s");

                    Query query = new Query();
                    query.setFirst_name(this.first_name);
                    query.setLast_name(this.last_name);
                    query.setPassword(this.password);
                    query.setId(this.id);
                    query.setGender(this.gender);
                    query.setAge(Integer.parseInt(this.age));
                    adapter.insertUserIntoSQL(connection, query, query_message);
                    signUpButtonAction(firstNameField, lastNameField, idNumberField,
                            passwordField, confirmPasswordField, ageField, maleRadioButton, femaleRadioButton,
                            studentRadioButton, teacherRadioButton, exit);
                    Stage currentStage = (Stage) signInButton.getScene().getWindow();
                    SignInSignUpApp signInSignUpApp = new SignInSignUpApp(currentStage);

                    Stage signinStage = new Stage();
                    signInSignUpApp.start(signinStage);
                    currentStage.close();
                } else {
                    throw new Exception("ne ucin");
                }
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }

        });

        registrationPane.getChildren().addAll(signUpLabel, firstNameField, lastNameField, idNumberField,
                passwordField, confirmPasswordField, ageField, maleRadioButton, femaleRadioButton,
                studentRadioButton, teacherRadioButton, exit, signInButton);

        exit.setOnAction(event -> {
            Stage currentStage = (Stage) exit.getScene().getWindow();
            currentStage.close();

            SignInSignUpApp signInSignUpApp = new SignInSignUpApp();
            Stage signInStage = new Stage();
            signInSignUpApp.start(signInStage);
        });


        root.getChildren().addAll(background, registrationPane);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    String table_name;
    public void insertRowToStudent(Connection connection) {
        PreparedStatement preparedStatement;
        try {
            String query = String.format("INSERT INTO %s (first_name, last_name, password, id, gender, age) VALUES (?, ?, ?, ?, ?, ?)",this.status+"s");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, id);
            preparedStatement.setString(5, gender);
            preparedStatement.setInt(6, Integer.parseInt(age));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void setupTextField(TextField textField, String promptText, double layoutX, double layoutY) {
        textField.setLayoutX(layoutX);
        textField.setLayoutY(layoutY);
        textField.setPrefSize(279, 36);
        textField.setPromptText(promptText);
        textField.setStyle("-fx-background-radius: 15;");
    }

    private void setupPasswordField(PasswordField passwordField, String promptText, double layoutX, double layoutY) {
        setupTextField(passwordField, promptText, layoutX, layoutY);
    }

    private void setupRadioButton(RadioButton radioButton, ToggleGroup toggleGroup, double layoutX, double layoutY) {
        radioButton.setLayoutX(layoutX);
        radioButton.setLayoutY(layoutY);
        radioButton.setMnemonicParsing(false);
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setFont(Font.font(16));
        radioButton.setCursor(Cursor.HAND);
    }

    private boolean showError = true;

    private void signUpButtonAction(TextField firstNameField, TextField lastNameField, TextField idNumberField,
                                    PasswordField passwordField, PasswordField confirmPasswordField,
                                    TextField ageField, RadioButton maleRadioButton, RadioButton femaleRadioButton,
                                    RadioButton studentRadioButton, RadioButton teacherRadioButton, Button exit) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String idNumber = idNumberField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String birthday = (!ageField.getText().isEmpty()) ? ageField.getText() : null;
        String gender = (maleRadioButton.isSelected() || femaleRadioButton.isSelected()) ?
                (maleRadioButton.isSelected() ? "Male" : "Female") : null;
        String userType = (studentRadioButton.isSelected() || teacherRadioButton.isSelected()) ?
                (studentRadioButton.isSelected() ? "Student" : "Teacher") : null;
        try{
            if (firstName.isEmpty() || lastName.isEmpty() || idNumber.isEmpty() || password.isEmpty()
                    || confirmPassword.isEmpty() || birthday == null || gender == null || userType == null) {
                throw new Exception("Please fill in all the required fields.");
            }

            if (password.length() < 8 || confirmPassword.length() < 8) {
                throw new Exception("Please make sure that password length is not less than 8.");
            }

            if (!password.equals(confirmPassword)) {
                throw new Exception("Please make sure the passwords match.");
            }

            this.first_name = firstNameField.getText();


            this.last_name = lastNameField.getText();

            this.id = Integer.parseInt(idNumberField.getText());

            this.password = passwordField.getText();

            this.age = ageField.getText();

            if (maleRadioButton.isSelected()) {
                this.gender = "male";
            } else if (femaleRadioButton.isSelected()) {
                this.gender = "female";

            }
            if (studentRadioButton.isSelected()) {
                this.status = "student";
            } else if (teacherRadioButton.isSelected())
                this.status = "teacher";
            insertRowToStudent(connection);

            showError = false;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("kate");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
