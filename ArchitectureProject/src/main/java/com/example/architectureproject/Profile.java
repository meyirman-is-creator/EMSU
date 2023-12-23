package com.example.architectureproject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Profile extends Application {
    private File selectedFile;
    User user;
    Connection connection = connection("designpatterns","designpatterns","Prvtmr1Kkvshdl2.");
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
    public void readData(Connection connection){
        Statement statement;
        ResultSet rs = null;
        try {
            String query="";
            if(user.getStatus().equals("students"))query=String.format("select distinct c.course_id, c.course_name, t.first_name,t.last_name from courses c join teachers t on t.id = c.teacher_id join enrollments e using(course_id) join students s on s.id = e.student_id where s.first_name = '%s'",user.getFirst_name());
            else if(user.getStatus().equals("teachers")) query=String.format("select t.first_name , t.last_name, c.course_id, c.course_name from teachers t join courses c on t.id = c.teacher_id where t.first_name = '%s'", user.getFirst_name());

            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()) {
                Course course = new Course();
                course.setCourse_name(rs.getString("course_name"));
                course.setCourse_id(Integer.parseInt(rs.getString("course_id")));
                course.setFirst_name(rs.getString("first_name"));
                course.setLast_name(rs.getString("last_name"));
                courses.add(course);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public Profile(Stage currentStage, User user) {
        this.user = user;
    }
    List<Course> courses = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        readData(connection);

        AnchorPane root = new AnchorPane();

        AnchorPane sidePane = new AnchorPane();
        sidePane.setPrefSize(86, 675);
        sidePane.setStyle("-fx-background-color: #1e1e2c;");

        TableView<Course> tableView = new TableView<>();

        TableColumn<Course, String> courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("course_name"));

        TableColumn<Course, Integer> courseIdColumn = new TableColumn<>("Course ID");
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("course_id"));

        TableColumn<Course, Integer> teacherFirstNameColumn = new TableColumn<>("First Name");
        teacherFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));

        TableColumn<Course, String> teacherLastNameColumn = new TableColumn<>("Last Name");
        teacherLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        courseNameColumn.setPrefWidth(140);
        courseIdColumn.setPrefWidth(140);
        teacherFirstNameColumn.setPrefWidth(140);
        teacherLastNameColumn.setPrefWidth(140);

        tableView.getColumns().add(courseNameColumn);
        tableView.getColumns().add(courseIdColumn);
        tableView.getColumns().add(teacherFirstNameColumn);
        tableView.getColumns().add(teacherLastNameColumn);
        tableView.setPrefHeight(565);
        tableView.setPrefWidth(570);
        tableView.setLayoutX(433);
        tableView.setLayoutY(92);
        // Предположим, что courses - это ваш список курсов
        ObservableList<Course> coursesO = FXCollections.observableArrayList(this.courses);



        tableView.setItems(coursesO);
        ImageView calendarIcon = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\icons8-календарь-50.png"));
        calendarIcon.setFitHeight(50);
        calendarIcon.setFitWidth(50);
        calendarIcon.setLayoutX(23);
        calendarIcon.setLayoutY(135);
        calendarIcon.setOpacity(0.34);

        ImageView booksIcon = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\icons8-книги-50 (1).png"));
        booksIcon.setFitHeight(50);
        booksIcon.setFitWidth(50);
        booksIcon.setLayoutX(23);
        booksIcon.setLayoutY(205);
        booksIcon.setOpacity(0.38);

        ImageView userIcon = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\icons8-пользователь-мужчина-в-кружке-50.png"));
        userIcon.setFitHeight(50);
        userIcon.setFitWidth(50);
        userIcon.setLayoutX(23);
        userIcon.setLayoutY(63);
        userIcon.setCursor(Cursor.HAND);

        ImageView exitIcon = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\icons8-выход-50.png"));
        exitIcon.setFitHeight(50);
        exitIcon.setFitWidth(50);
        exitIcon.setLayoutX(23);
        exitIcon.setLayoutY(610);
        exitIcon.setOpacity(0.28);

        Button button1 = new Button();
        button1.setLayoutX(23);
        button1.setLayoutY(82);
        button1.setPrefSize(50, 50);
        button1.setOpacity(0.0);
        button1.setCursor(Cursor.HAND);

        Button button2 = new Button();
        button2.setLayoutX(23);
        button2.setLayoutY(147);
        button2.setPrefSize(50, 50);
        button2.setOpacity(0.0);
        button2.setCursor(Cursor.HAND);

        Button button3 = new Button("Button");
        button3.setLayoutX(23);
        button3.setLayoutY(209);
        button3.setPrefSize(50, 50);
        button3.setOpacity(0.0);
        button3.setCursor(Cursor.HAND);

        Button button4 = new Button();
        button4.setLayoutX(23);
        button4.setLayoutY(610);
        button4.setPrefSize(50, 50);
        button4.setOpacity(0.0);
        button4.setCursor(Cursor.HAND);

        sidePane.getChildren().addAll(calendarIcon, booksIcon, userIcon, exitIcon, button1, button2, button3, button4);

        Accordion accordion = new Accordion();
        accordion.setLayoutX(293);
        accordion.setLayoutY(143);

        Rectangle rectangle1 = new Rectangle(290, 574);
        rectangle1.setArcHeight(10);
        rectangle1.setArcWidth(5);
        rectangle1.setFill(javafx.scene.paint.Color.WHITE);
        rectangle1.setLayoutX(120);
        rectangle1.setLayoutY(88);
        rectangle1.setOpacity(0.25);
        rectangle1.setStroke(javafx.scene.paint.Color.BLACK);
        rectangle1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rectangle1.setStrokeWidth(3);


        ImageView avatarImage = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\simple-avatar.png"));
        avatarImage.setFitHeight(123);
        avatarImage.setFitWidth(124);
        avatarImage.setLayoutX(209);
        avatarImage.setLayoutY(109);
        avatarImage.setOpacity(0.68);



        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setFont(new Font("System Bold", 18));
        lastNameLabel.setLayoutX(140);
        lastNameLabel.setLayoutY(336);
        Label userLastName = new Label(user.getLast_name());
        userLastName.setFont(new Font("System Bold", 18));
        userLastName.setLayoutX(255);
        userLastName.setLayoutY(336);

        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.setFont(new Font("System Bold", 18));
        firstNameLabel.setLayoutX(140);
        firstNameLabel.setLayoutY(292);
        Label userFirstName = new Label(user.getFirst_name());
        userFirstName.setFont(new Font("System Bold", 18));
        userFirstName.setLayoutX(255);
        userFirstName.setLayoutY(292);

        Label idNumberLabel = new Label("Id Number:");
        idNumberLabel.setFont(new Font("System Bold", 18));
        idNumberLabel.setLayoutX(140);
        idNumberLabel.setLayoutY(388);
        Label userId = new Label(String.valueOf(user.getId()));
        userId.setFont(new Font("System Bold", 18));
        userId.setLayoutX(255);
        userId.setLayoutY(388);

        Label birthdayLabel = new Label("Age:");
        birthdayLabel.setFont(new Font("System Bold", 18));
        birthdayLabel.setLayoutX(140);
        birthdayLabel.setLayoutY(542);
        Label userAge = new Label(String.valueOf(user.getAge()));
        userAge.setFont(new Font("System Bold", 18));
        userAge.setLayoutX(255);
        userAge.setLayoutY(542);

        Label genderLabel = new Label("Gender:");
        genderLabel.setFont(new Font("System Bold", 18));
        genderLabel.setLayoutX(140);
        genderLabel.setLayoutY(439);
        Label userGender = new Label(user.getGender());
        userGender.setFont(new Font("System Bold", 18));
        userGender.setLayoutX(255);
        userGender.setLayoutY(439);

        Label statusLabel = new Label("Status:");
        statusLabel.setFont(new Font("System Bold", 18));
        statusLabel.setLayoutX(140);
        statusLabel.setLayoutY(491);
        Label userStatus = new Label(user.getStatus());
        userStatus.setFont(new Font("System Bold", 18));
        userStatus.setLayoutX(255);
        userStatus.setLayoutY(491);



        Label myProfileLabel = new Label("My Profile");
        myProfileLabel.setFont(new Font(31));
        myProfileLabel.setLayoutX(120);
        myProfileLabel.setLayoutY(24);
        Label myCourses = new Label("My Course");
        myCourses.setFont(new Font(31));
        myCourses.setLayoutX(430);
        myCourses.setLayoutY(24);

        button2.setOnAction(event -> {
            Stage currentStage = (Stage) button2.getScene().getWindow();
            currentStage.close();

            try {
                if(user.getStatus().equals("students")) {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/demo6/calendar.fxml"));
                    Parent root1 = loader.load();
                    StudentCalendar calendarController = loader.getController();

                    Platform.runLater(() -> {
                        calendarController.addUser(user, user.getFirst_name());
                        primaryStage.setScene(new Scene(root1));
                        primaryStage.setTitle("Calendar");
                        primaryStage.show();
                    });
                } else if(user.getStatus().equals("teachers")){
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
        });

        button3.setOnAction(event -> {
            Stage currentStage = (Stage) button3.getScene().getWindow();
            currentStage.close();

            Courses courses1 = new Courses(currentStage, user);
            Stage courseStage = new Stage();
            courses1.start(courseStage);
        });

        button4.setOnAction(event -> {
            Stage currentStage = (Stage) button3.getScene().getWindow();
            Outing outing1 = new Outing(currentStage);
            Stage outingStage = new Stage();
            outing1.start(outingStage);
        });



        root.getChildren().addAll(tableView, rectangle1,
                sidePane, accordion, avatarImage,
                lastNameLabel,userLastName, userAge,userFirstName,userStatus,userGender,userId,
                firstNameLabel, idNumberLabel, birthdayLabel, genderLabel, statusLabel,
                myProfileLabel, myCourses
        );

        Scene scene = new Scene(root, 1029, 675);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Your JavaFX Application");
        primaryStage.show();
        }

    public static void main(String[] args) {
        launch(args);
    }
}


