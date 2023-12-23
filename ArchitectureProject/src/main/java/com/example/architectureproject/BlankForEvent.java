package com.example.architectureproject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class BlankForEvent{
    public TextField description;
    public TextField eventName;
    public TextField placement;
    public DatePicker eventDate;

    @FXML
    private ComboBox<String> myCombobox;
    @FXML
    private TextField time;
    Stage stage;
    User user;
    void addUser(User user,List<Course> courses,Stage stage) {
        this.user = user;
        this.stage =stage;
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < courses.size(); i++){
            String name = courses.get(i).getCourse_name() + " " + courses.get(i).getCourse_id();
            items.add(name);
        }
        myCombobox.setItems(items);
    }
    String[] course;
    @FXML
    void addEvent(ActionEvent actionEvent) {
        SQLDatabaseService dbService = new SQLDatabaseService();
        SQLAdapter adapter = new SQLDatabaseAdapter(dbService);
        Connection connection = adapter.getConnection();
        try{
            if (eventName.getText().isEmpty())
                throw new Exception("EventName is empty!!!");
            if(description.getText().isEmpty())
                throw new Exception("Description is empty!!!");
            if(placement.getText().isEmpty())
                throw new Exception("Placement is empty!!!");

            course = myCombobox.getValue().split(" ");
            String query_message = "INSERT INTO events (event_id, description, event_name, event_date, placement, course_id, teacher_id, время) VALUES (?,?,?,?,?,?,?,?)";
            Query query = new Query();
            Random random = new Random();
            int r = random.nextInt(1000,2000);
            query.setEvent_id(r);
            query.setDescription(description.getText());
            query.setEvent_name(eventName.getText());
            query.setEvent_date(Date.valueOf(eventDate.getValue()));
            query.setPlacement(placement.getText());
            query.setCourse_id(Integer.parseInt(course[1]));
            query.setTeacher_id(user.getId());
            query.setВремя(time.getText());
            adapter.insertAllIntoSQL(connection, query, query_message);

            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/demo6/calendar1.fxml"));
                Parent root1 = loader.load();
                TeacherCalendar calendarController1 = loader.getController();
                calendarController1.addUser(user,user.getFirst_name());
                Stage stage1 = new Stage();
                stage1.setTitle("Новое окно");
                stage1.setScene(new Scene(root1));
                this.stage.close();
                stage1.show();
            }catch (Exception e){
                System.err.println(e.getMessage());
            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }

    }



    @FXML
    void openCourses(ActionEvent event) {

    }
}
