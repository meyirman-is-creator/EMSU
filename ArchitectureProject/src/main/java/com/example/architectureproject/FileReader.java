package com.example.architectureproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface FileReader {

    void read(File file, Stage stage);
}

class CSVFileReader implements FileReader {
    Stage stage;

    @Override
    public void read(File file,Stage stage) {
        SQLDatabaseService dbService = new SQLDatabaseService();
        SQLAdapter adapter = new SQLDatabaseAdapter(dbService);
        Connection connection = adapter.getConnection();
        this.stage = stage;
        String query_message = "INSERT INTO events (event_id, description, event_name, event_date, placement, course_id, teacher_id, время) VALUES (?,?,?,?,?,?,?,?)";

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Query query = new Query();
                query.setEvent_id(Integer.parseInt(data[0]));
                query.setDescription(data[1]);
                query.setEvent_name(data[2]);
                query.setEvent_date(Date.valueOf(data[3]));
                query.setPlacement(data[4]);
                query.setCourse_id(Integer.parseInt(data[5]));
                query.setTeacher_id(Integer.parseInt(data[6]));
                query.setВремя(data[7]);
                adapter.insertAllIntoSQL(connection, query, query_message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

class XLSXFileReader implements FileReader {
    Stage stage;
    @Override
    public void read(File file1,Stage stage) {
        SQLDatabaseService dbService = new SQLDatabaseService();
        SQLAdapter adapter = new SQLDatabaseAdapter(dbService);
        Connection connection = adapter.getConnection();
        String query_message = "INSERT INTO events (event_id, description, event_name, event_date, placement, course_id, teacher_id, время) VALUES (?,?,?,?,?,?,?,?)";
        this.stage = stage;
        DataFormatter formatter = new DataFormatter();
        try (FileInputStream file = new FileInputStream(file1)) {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                List<String> str = new ArrayList<>();
                for (Cell cell : row) {
                    String text = formatter.formatCellValue(cell);
                    str.add(text);
                }
                Query query = new Query();
                query.setEvent_id(Integer.parseInt(str.get(0)));
                query.setDescription(str.get(1));
                query.setEvent_name(str.get(2));
                query.setEvent_date(Date.valueOf(str.get(3)));
                query.setPlacement(str.get(4));
                query.setCourse_id(Integer.parseInt(str.get(5)));
                query.setTeacher_id(Integer.parseInt(str.get(6)));
                query.setВремя(str.get(7));
                adapter.insertAllIntoSQL(connection, query, query_message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class FileReaderFactory {
    public static FileReader getFileReader(File file) {
        if (file.getName().toLowerCase().endsWith(".csv")) {
            return new CSVFileReader();
        } else if (file.getName().toLowerCase().endsWith(".xlsx")) {
            return new XLSXFileReader();
        }
        throw new IllegalArgumentException("Неизвестный формат файла: " + file.getName());
    }
}
