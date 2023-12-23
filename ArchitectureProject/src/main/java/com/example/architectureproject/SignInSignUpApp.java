package com.example.architectureproject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.css.Size;
import javafx.css.Style;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SignInSignUpApp extends Application {
    private ArrayList<User> userList = new ArrayList<>();

    public SignInSignUpApp() {
    }

    public SignInSignUpApp(Stage currentStage) {
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
    public void readData(Connection connection, String table_name){
        Statement statement;
        ResultSet rs = null;
        try {
            String query=String.format("select * from %s",table_name);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                User user = new User();
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setAge(Integer.parseInt(rs.getString("age")));
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setGender(rs.getString("gender"));
                user.setPassword(rs.getString("password"));
                userList.add(user);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void start(Stage primaryStage) {
        Connection connection = connection("designpatterns","designpatterns","Prvtmr1Kkvshdl2.");
        readData(connection,"students");
        primaryStage.setTitle("Sign In/Sign Up");

        AnchorPane root = new AnchorPane();
        root.setPrefSize(1029, 675);

        ImageView background = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\MainPhoto.jpeg"));
        background.setFitWidth(1038);
        background.setFitHeight(702);

        AnchorPane signUpPane = new AnchorPane();
        signUpPane.setLayoutX(512);
        signUpPane.setLayoutY(128);
        signUpPane.setOpacity(0.82);
        signUpPane.setPrefSize(486, 404);
        signUpPane.setStyle("-fx-background-color: #D3D3D3; -fx-background-radius: 5px;");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setLayoutX(206);
        signUpButton.setLayoutY(307);
        signUpButton.setPrefSize(108, 29);
        signUpButton.setStyle("-fx-background-radius: 25; -fx-border-image-width: 40;");
        signUpButton.setTextFill(javafx.scene.paint.Color.web("#5e5e5e"));
        signUpButton.setFont(Font.font("System Bold", 20));
        signUpButton.setCursor(Cursor.HAND);

        Label signUpLabel = new Label("Sign Up");
        signUpLabel.setLayoutX(193);
        signUpLabel.setLayoutY(65);
        signUpLabel.setPrefSize(134, 51);
        signUpLabel.setFont(Font.font("System Bold", 35));
        signUpLabel.setTextFill(javafx.scene.paint.Color.web("#65645f"));

        Text signUpText = new Text("If you do not have an account, please register. Click on the button below");
        signUpText.setLayoutX(54);
        signUpText.setLayoutY(164);
        signUpText.setWrappingWidth(378);
        signUpText.setFont(Font.font("System Bold", 20));
        signUpText.setFill(javafx.scene.paint.Color.web("#393838"));

        signUpPane.getChildren().addAll(signUpButton, signUpLabel, signUpText);

        AnchorPane signInPane = new AnchorPane();
        signInPane.setLayoutX(29);
        signInPane.setLayoutY(97);
        signInPane.setPrefSize(486, 465);
        signInPane.setStyle("-fx-background-color: white;");

        Button signInButton = new Button("Sign In");
        signInButton.setLayoutX(176);
        signInButton.setLayoutY(329);
        signInButton.setPrefSize(134, 45);
        signInButton.setStyle("-fx-background-radius: 25; -fx-border-image-width: 0;");
        signInButton.setTextFill(javafx.scene.paint.Color.web("#5e5e5e"));
        signInButton.setFont(Font.font("System Bold", 20));
        signInButton.setCursor(Cursor.HAND);

        Label signInLabel = new Label("Sign In ");
        signInLabel.setLayoutX(176);
        signInLabel.setLayoutY(63);
        signInLabel.setPrefSize(134, 51);
        signInLabel.setFont(Font.font("System Bold", 35));
        signInLabel.setTextFill(javafx.scene.paint.Color.web("#757575"));

        TextField idNumberField = new TextField();
        setupTextField(idNumberField, "ID Number", 96, 161);

        PasswordField passwordField = new PasswordField();
        setupTextField(passwordField, "Password", 96, 233);
        ToggleGroup typeToggleGroup = new ToggleGroup();

        RadioButton studentRadioButton = new RadioButton("Student");
        setupRadioButton(studentRadioButton, typeToggleGroup, 95, 290);

        RadioButton teacherRadioButton = new RadioButton("Teacher");
        setupRadioButton(teacherRadioButton, typeToggleGroup, 280, 290);


        signUpButton.setOnAction(event -> {
            Stage currentStage = (Stage) signUpButton.getScene().getWindow();
            currentStage.close();

            RegistrationApp registrationApp = new RegistrationApp(currentStage);
            Stage registrationStage = new Stage();
            registrationApp.start(registrationStage);
        });
        Label errorPassword = new Label();
        errorPassword.setText("Password or ID Number is not correct");
        errorPassword.setVisible(false);
        errorPassword.setTextFill(Color.RED);
        errorPassword.setLayoutX(150);
        errorPassword.setLayoutY(380);


        signInButton.setOnAction(event -> {

            userList.clear();
            String tableName = "";
            if(studentRadioButton.isSelected()) tableName = "students";
            else if (teacherRadioButton.isSelected()) tableName = "teachers";
            readData(connection,tableName);
            try{
                if(!idNumberField.getText().isEmpty() && !passwordField.getText().isEmpty()){
                    String userId = idNumberField.getText();
                    String password = passwordField.getText();
                    for(int i=0;i<userList.size();i++){
                        if(userList.get(i).getPassword().equals(password) && userList.get(i).getId()==Integer.parseInt(userId)){
                            User user = userList.get(i);
                            user.setStatus(tableName);
                            try {
                                if(tableName.equals("students")) {
                                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/demo6/calendar.fxml"));
                                    Parent root1 = loader.load();
                                    StudentCalendar calendarController = loader.getController();

                                    Platform.runLater(() -> {
                                        calendarController.addUser(user, user.getFirst_name());
                                        primaryStage.setScene(new Scene(root1));
                                        primaryStage.setTitle("Calendar");
                                        primaryStage.show();
                                    });
                                } else if(tableName.equals("teachers")){
                                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/demo6/calendar1.fxml"));
                                    Parent root1 = loader.load();
                                    TeacherCalendar calendarController1 = loader.getController();

                                    Platform.runLater(() -> {
                                        calendarController1.addUser(user,user.getFirst_name());
                                        primaryStage.setScene(new Scene(root1));
                                        primaryStage.setTitle("Calendar");
                                        primaryStage.show();
                                    });
                                }
                            } catch (Exception e){
                                System.out.println(e.getMessage());
                            }

                            return;
                        }
                    }
                    throw new Exception("kate");


                } else throw new Exception("kate");

            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }


        });

        signInPane.getChildren().addAll(signInButton, signInLabel, idNumberField, passwordField, studentRadioButton, teacherRadioButton);

        root.getChildren().addAll(background, signUpPane, signInPane, errorPassword);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private boolean validateUser(String userId, String password) {
        for (User user : userList) {
            if (String.valueOf(user.getId()).equals(userId) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    private void setupRadioButton(RadioButton radioButton, ToggleGroup toggleGroup, double layoutX, double layoutY) {
        radioButton.setLayoutX(layoutX);
        radioButton.setLayoutY(layoutY);
        radioButton.setMnemonicParsing(false);
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setFont(Font.font(16));
        radioButton.setCursor(Cursor.HAND);
    }

    private void setupTextField(TextField textField, String promptText, double layoutX, double layoutY) {
        textField.setLayoutX(layoutX);
        textField.setLayoutY(layoutY);
        textField.setPrefSize(279, 36);
        textField.setPromptText(promptText);
        textField.setStyle("-fx-background-radius: 15;");

    }

}
