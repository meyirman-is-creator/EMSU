package com.example.architectureproject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Cursor;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Courses extends Application {

    User user;
    public Courses(Stage currentStage,User user) {
        this.user = user;
    }
    List<Course> courses = new ArrayList<>();
    List<Course> jokCourse = new ArrayList<>();

    List<Course> addDrop = new ArrayList<>();
    @Override
    public void start(Stage primaryStage) {
        SQLDatabaseService dbService = new SQLDatabaseService();
        SQLAdapter adapter = new SQLDatabaseAdapter(dbService);
        Connection connection = adapter.getConnection();
        Query query = new Query();
        query.setStatus(user.getStatus());
        query.setFirst_name(user.getFirst_name());
        courses = adapter.selectFromSQL(connection,courses,query,true);
        jokCourse = adapter.selectFromSQL(connection,courses,query,false);
        AnchorPane root = new AnchorPane();
        root.setMaxHeight(Double.NEGATIVE_INFINITY);
        root.setMaxWidth(Double.NEGATIVE_INFINITY);
        root.setMinHeight(Double.NEGATIVE_INFINITY);
        root.setMinWidth(Double.NEGATIVE_INFINITY);
        root.setPrefHeight(675.0);
        root.setPrefWidth(730.0);
        root.setStyle("-fx-background-color: #F0FFFF;");

        AnchorPane leftPane = new AnchorPane();
        leftPane.setLayoutX(-6.0);
        leftPane.setPrefHeight(675.0);
        leftPane.setPrefWidth(86.0);
        leftPane.setStyle("-fx-background-color: #1e1e2c;");

        Button button1 = createButton(26.0, 65.0, 38.0, 38.0, 0.0);
        button1.setCursor(Cursor.HAND);
        ImageView adam = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\icons8-пользователь-мужчина-в-кружке-50.png"));
        adam.setLayoutX(26.0);
        adam.setLayoutY(65.0);
        adam.prefWidth(30.0);
        adam.prefHeight(30.0);
        adam.setOpacity(0.34);

        Button button2 = createButton(26.0, 143.0, 38.0, 31.0, 0.0);
        button2.setCursor(Cursor.HAND);
        ImageView calendar = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\icons8-календарь-50.png"));
        calendar.setLayoutX(26.0);
        calendar.setLayoutY(143.0);
        calendar.prefHeight(30.0);
        calendar.prefWidth(30.0);
        calendar.setOpacity(0.34);

        Button button3 = createButton(26.0, 214.0, 38.0, 31.0, 0.0);
        button3.setText("Button");
        button3.setCursor(Cursor.HAND);
        ImageView books = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\icons8-книги-50 (1).png"));
        books.setLayoutX(26.0);
        books.setLayoutY(214.0);
        books.prefWidth(30.0);
        books.prefHeight(30.0);
        books.setOpacity(1.0);

        Button button4 = createButton(26.0, 610.0, 38.0, 31.0, 0.0);
        button4.setCursor(Cursor.HAND);
        ImageView outing = new ImageView(new Image("D:\\my-files\\Program1\\design_pattern_project\\ArchitectureProject\\Files\\icons8-выход-50.png"));
        outing.setLayoutX(26.0);
        outing.setLayoutY(610.0);
        outing.prefWidth(30.0);
        outing.prefHeight(30.0);
        outing.setOpacity(0.34);
        Button confirm = createButton(86.0, 645, 640, 30.0, 1.0);
        confirm.setBackground(Background.fill(Color.RED));
        confirm.setTextFill(Color.WHITE);
        confirm.setText("CONFIRM");
        confirm.setCursor(Cursor.HAND);

        TableView<Course> tableView = new TableView<>();

        TableColumn<Course, String> courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("course_name"));

        TableColumn<Course, Integer> courseIdColumn = new TableColumn<>("Course ID");
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("course_id"));

        TableColumn<Course, Integer> teacherFirstNameColumn = new TableColumn<>("First Name");
        teacherFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));

        TableColumn<Course, String> teacherLastNameColumn = new TableColumn<>("Last Name");
        teacherLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        TableColumn<Course, Void> actionCol = new TableColumn<>(" ");
        actionCol.setCellFactory(col -> new TableCell<Course, Void>() {
            private final Button actionButton = new Button("ADD");


            {
                actionButton.setPrefWidth(60);
                actionButton.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    if(addDrop.contains(course)){
                        actionButton.setText("ADD  ");
                        addDrop.remove(course);
                    } else{
                        actionButton.setText("DROP ");
                        addDrop.add(course);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionButton);
                }
            }
        });

        courseNameColumn.setPrefWidth(140);
        courseIdColumn.setPrefWidth(140);
        teacherFirstNameColumn.setPrefWidth(140);
        teacherLastNameColumn.setPrefWidth(140);

        tableView.getColumns().add(courseNameColumn);
        tableView.getColumns().add(courseIdColumn);
        tableView.getColumns().add(teacherFirstNameColumn);
        tableView.getColumns().add(teacherLastNameColumn);
        tableView.getColumns().add(actionCol);
        tableView.setPrefHeight(645);
        tableView.setPrefWidth(640);
        tableView.setLayoutX(80);
        tableView.setLayoutY(0);
        ObservableList<Course> coursesO = null;
        if(user.getStatus().equals("students"))coursesO = FXCollections.observableArrayList(this.jokCourse);
        else if(user.getStatus().equals("teachers"))coursesO = FXCollections.observableArrayList(this.courses);


        tableView.setItems(coursesO);


        leftPane.getChildren().addAll(button1, button2, button3, button4, adam, calendar, books, outing,confirm);
        confirm.setOnAction(e->{

            jokCourse.clear();
            this.courses.clear();
            String query_message="";
            if(user.getStatus().equals("students"))query_message="INSERT INTO enrollments (student_id,course_id) VALUES (?, ?)";
            else if(user.getStatus().equals("teachers")) query_message="update courses set teacher_id = ? where course_id = ?";
            for(int i=0;i<addDrop.size();i++){
                Query query1 = new Query();
                query1.setId(user.getId());
                query1.setCourse_id(addDrop.get(i).getCourse_id());
                adapter.insertCourseIntoSQL(connection, query1, query_message);
            }
            Stage stage = (Stage) (confirm.getScene().getWindow());
            stage.close();
            Courses courses1 = new Courses(new Stage(), user);
            Stage courseStage = new Stage();
            courses1.start(courseStage);
        });
        button1.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            Profile profile = new Profile(stage, user);
            profile.start(new Stage());
        });

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
        button4.setOnAction(event -> {
            Stage currentStage = (Stage) button3.getScene().getWindow();
            Outing outing1 = new Outing(currentStage);

            Stage outingStage = new Stage();
            outing1.start(outingStage);
        });


        Accordion accordion = new Accordion();
        accordion.setLayoutX(293.0);
        accordion.setLayoutY(143.0);

        root.getChildren().addAll(leftPane, accordion, tableView);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(double layoutX, double layoutY, double prefWidth, double prefHeight, double opacity) {
        Button button = new Button();
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setPrefWidth(prefWidth);
        button.setPrefHeight(prefHeight);
        button.setOpacity(opacity);
        button.setMnemonicParsing(false);
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
