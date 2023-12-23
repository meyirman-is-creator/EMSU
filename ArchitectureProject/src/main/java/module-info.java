module com.example.architectureproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires org.apache.poi.ooxml;


    opens com.example.architectureproject to javafx.fxml;
    exports com.example.architectureproject;
}