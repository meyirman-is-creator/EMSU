package com.example.architectureproject;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class StudentCalendar implements Initializable {

    public AnchorPane goToCourse;
    public AnchorPane goToProfile;
    public AnchorPane goToExit;
    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @FXML
    private VBox salu;
    @FXML
    private VBox description;
    User user;
    String table_name;

    List<Course> coursesList = new ArrayList<>();

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
            if(user.getStatus().equals("students")) query = String.format("select s.first_name, s.last_name, e1.event_id, e1.event_name, e1.event_date, e1.description, e1.placement, e1.время,c.course_id,c.teacher_id  from students s join enrollments e on e.student_id = s.id join courses c using(course_id) join events e1 using(course_id) where s.first_name = '%s'\n",user.getFirst_name());
            else if(user.getStatus().equals("teachers")) query = String.format("select e.event_id,e.description,e.event_name, e.event_date , placement, course_id, teacher_id, время from events e join teachers t on e.teacher_id = t.id where t.first_name = '%s'",user.getFirst_name());
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                int event_id = Integer.parseInt(rs.getString("event_id"));
                String description = rs.getString("description");
                String event_name = rs.getString("event_name");
                String placement = rs.getString("placement");
                int course_id = Integer.parseInt(rs.getString("course_id"));
                int teacher_id = Integer.parseInt(rs.getString("teacher_id"));
                String[] time  = rs.getString("время").split(":");
                String key = rs.getString("event_date");
                String[] event_date = rs.getString("event_date").split("-");
                LocalDateTime localDateTime19 = LocalDateTime.of(Integer.parseInt(event_date[0]), Integer.parseInt(event_date[1]), Integer.parseInt(event_date[2]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
                ZoneId zoneId = ZoneId.of("Asia/Almaty");
                ZonedDateTime zonedDateTime19 = ZonedDateTime.of(localDateTime19, zoneId);
                Events events = new Events();
                events.setEventDate(zonedDateTime19);
                events.setEventName(event_name);
                events.setDescription(description);
                events.setPlacement(placement);
                events.setEvent_id(event_id);
                events.setTeacher_id(teacher_id);
                Course course = null;
                for(int i=0;i<coursesList.size(); i++){
                    if(coursesList.get(i).getCourse_id()==course_id){
                        course = coursesList.get(i);
                    }
                }
                events.setCourse(course);
                if(calendarActivityMap.containsKey(key)){
                    int r =0;
                    for(int i= 0;i<calendarActivityMap.get(key).size();i++){
                        if(calendarActivityMap.get(key).get(i).event_id==events.event_id)r++;
                    }
                    if(r==0)calendarActivityMap.get(key).add(events);
                }else{
                    List<Events> eventsList = new ArrayList<>();
                    eventsList.add(events);
                    calendarActivityMap.put(key, eventsList);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
        calendar.getChildren().clear();
        drawCalendar();
    }


    public void addUser(User user, String table_name) {
        this.user = user;
        this.table_name = table_name;
        initialize(null, null);
    }
    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();


    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }
    static Map<String, List<Events>> calendarActivityMap = new HashMap<>();
    Connection connection = connection("designpatterns","designpatterns","Prvtmr1Kkvshdl2.");

    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));
        double strokeWidth = 1;
        readData(connection);
        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setStroke(Paint.valueOf("#000"));
                rectangle.setFill(Paint.valueOf("#fff"));
                rectangle.setOpacity(100.00);

                rectangle.setStrokeWidth(strokeWidth);
                rectangle.setWidth(92);
                rectangle.setHeight(77);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (77 / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);
                        String type = year.getText()+"-";
                        switch (month.getText()){
                            case "DECEMBER": type+="12"; break;
                            case "NOVEMBER": type+="11"; break;
                            case "OCTOBER":type+="10"; break;
                            case "SEPTEMBER":type+="09"; break;
                            case "AUGUST":type+="08";break;
                            case "JULY": type+="07"; break;
                            case "JUNE": type+="06"; break;
                            case "MAY":type+="05"; break;
                            case "APRIL":type+="04"; break;
                            case "MARCH":type+="03";break;
                            case "FEBRUARY": type+="02"; break;
                            case "JANUARY": type+="01"; break;
                        }
                        if(currentDate<10)type+="-0"+currentDate;
                        else type+="-"+currentDate;

                        List<Events> calendarActivities =  calendarActivityMap.get(type);
                        if(calendarActivities != null){
                            createCalendarActivity(calendarActivities, 77, 92, stackPane);
                        }


                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Paint.valueOf("#1e1e2c"));
                        rectangle.setFill(Paint.valueOf("#a0277f"));

                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<Events> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if(k >= 2) {
                Text moreActivities = new Text("more...");
                moreActivities.setFill(Paint.valueOf("#fff"));

                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date

                });
                break;
            }
            String textstr= calendarActivities.get(k).getEventName();
            if(textstr.length()>10){
                textstr = textstr.substring(0,10);
                textstr+="...";
            }
            Text text = new Text(textstr);
            text.setFill(Paint.valueOf("#fff"));
            calendarActivityBox.getChildren().add(text);

            description.setPadding(new Insets(10));
            stackPane.setOnMouseClicked(mouseEvent -> {

                salu.getChildren().clear();
                description.getChildren().clear();
                //On Text clicked''
                VBox pane = new VBox();
                pane.paddingProperty().set(new Insets(10));
                for(int i=0;i<calendarActivities.size();i++){
                    String temp = calendarActivities.get(i).getEventName()+", "+calendarActivities.get(i).getEventDate().toLocalTime()+", "+calendarActivities.get(i).getPlacement()+", "+ ChronoUnit.DAYS.between(today,calendarActivities.get(i).getEventDate())+" day"+"\n";
                    Text text1 = new Text(temp);
                    text1.setFont(new Font("Arial", 15));
                    text1.setFill(Paint.valueOf("#000"));
                    text1.setUnderline(true);
                    String d = calendarActivities.get(i).getDescription();
                    text1.setOnMouseClicked(mouseEvent1 -> {
                        description.getChildren().clear();
                        Text text2 = new Text(d);
                        text2.setFont(new Font("Arial", 20));
                        text2.setFill(Paint.valueOf("#000"));
                        text2.setWrappingWidth(300);
                        description.getChildren().add(text2);

                    });

                    pane.getChildren().add(text1);


                }
                salu.getChildren().add(pane);






            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setOpacity(1.0);
        calendarActivityBox.setStyle("-fx-background-color:#1e1e2c");
        stackPane.getChildren().add(calendarActivityBox);
    }


    @FXML
    void goToCourse(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        Courses courses = new Courses(stage, user);
        Stage courseStage = new Stage();
        courses.start(courseStage);
    }

    @FXML
    void goToExit(ActionEvent event) {
        coursesList.clear();
        calendarActivityMap.clear();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Outing outing1 = new Outing(currentStage);
        Stage outingStage = new Stage();
        outing1.start(outingStage);
    }

    @FXML
    void goToProfile(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        Profile profile = new Profile(stage, user);
        profile.start(new Stage());
    }
}

