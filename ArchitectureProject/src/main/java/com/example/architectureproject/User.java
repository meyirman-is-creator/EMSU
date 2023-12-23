package com.example.architectureproject;

public class User {
    private String first_name;
    private String last_name;
    private String password;
    private int id;
    private String gender;
    private int age;
    public User(){};
    private String status;
    public User(String first_name, String last_name, String password, int id, String gender, int age,String status) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.status =status;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
