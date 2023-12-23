package com.example.architectureproject;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface SQLAdapter {

    void insertAllIntoSQL(Connection connection, Query query, String query_message);

    Connection connection(String dbName, String user, String password);

    Connection getConnection();

    void insertCourseIntoSQL(Connection connection, Query query, String query_message);

    void insertUserIntoSQL(Connection connection, Query query, String query_message);
    List<Course> selectFromSQL(Connection connection,List<Course> courses, Query query, boolean bool);

}
class SQLDatabaseService {
    Connection connection;
    public SQLDatabaseService(){
        connection = connection("designpatterns","designpatterns","Prvtmr1Kkvshdl2.");

    }

    public Connection getConnection() {
        return connection;
    }

    List<Course> selectFromSQL(Connection connection,List<Course> courses, Query query, boolean bool){
        List<Course> jokCourse = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query_message="";
            if(query.getStatus().equals("students")) {
                if(!bool)query_message = String.format("select distinct c.course_name, c.course_id, t.first_name, t.last_name from courses c left join teachers t on t.id = c.teacher_id where t.id is not null\n", query.getFirst_name());
                else query_message=String.format("select distinct c.course_id, c.course_name, t.first_name,t.last_name from courses c join teachers t on t.id = c.teacher_id join enrollments e using(course_id) join students s on s.id = e.student_id where s.first_name = '%s'",query.getFirst_name());
            }
            else if(query.getStatus().equals("teachers")) query_message="select c.course_name,c.course_id,t.first_name, t.last_name from courses c left join teachers t on c.teacher_id = t.id where c.teacher_id is null\n";

            statement = connection.createStatement();
            rs = statement.executeQuery(query_message);
            while(rs.next()) {
                Course course = new Course();
                course.setCourse_name(rs.getString("course_name"));
                course.setCourse_id(Integer.parseInt(rs.getString("course_id")));
                course.setFirst_name(rs.getString("first_name"));
                course.setLast_name(rs.getString("last_name"));
                int r =0;
                for(int i=0;i<courses.size(); i++){
                    if(courses.get(i).getCourse_id() == course.getCourse_id())r++;
                }
                if(r==0&& !jokCourse.contains(course))jokCourse.add(course);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return jokCourse;
    }

    public void addAllData(Connection connection, Query query,String query_message) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query_message);
            preparedStatement.setInt(1, query.getEvent_id());
            preparedStatement.setString(2, query.getDescription());
            preparedStatement.setString(3, query.getEvent_name());
            preparedStatement.setDate(4, query.getEvent_date());
            preparedStatement.setString(5,query.getPlacement());
            preparedStatement.setInt(6,query.getCourse_id());
            preparedStatement.setInt(7,query.getTeacher_id());
            preparedStatement.setString(8,query.getВремя());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void addCourse(Connection connection, Query query,String query_message) {
        PreparedStatement preparedStatement;
        try {

            preparedStatement = connection.prepareStatement(query_message);
            preparedStatement.setInt(1, query.getId());
            preparedStatement.setInt(2, query.getCourse_id());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void addUser(Connection connection, Query query,String query_message) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query_message);
            preparedStatement.setString(1, query.getFirst_name());
            preparedStatement.setString(2, query.getLast_name());
            preparedStatement.setString(3, query.getPassword());
            preparedStatement.setInt(4, query.getId());
            preparedStatement.setString(5, query.getGender());
            preparedStatement.setInt(6,query.getAge());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
}
class SQLDatabaseAdapter implements SQLAdapter {
    private final SQLDatabaseService dbService;
    public SQLDatabaseAdapter(SQLDatabaseService dbService) {

        this.dbService = dbService;
    }

    @Override
    public List<Course> selectFromSQL(Connection connection, List<Course> courses, Query query, boolean bool){
        return dbService.selectFromSQL(connection,courses,query,bool);

    }

    @Override
    public void insertAllIntoSQL(Connection connection, Query query,String query_message) {
        dbService.addAllData(connection, query,query_message);
    }

    @Override
    public void insertCourseIntoSQL(Connection connection, Query query,String query_message) {
        dbService.addCourse(connection, query,query_message);
    }
    @Override
    public void insertUserIntoSQL(Connection connection, Query query,String query_message) {
        dbService.addUser(connection, query,query_message);
    }
    public Connection getConnection() {
        return dbService.getConnection();
    }

    @Override
    public Connection connection(String dbName, String user, String password) {
        return dbService.connection(dbName, user, password);
    }
}
