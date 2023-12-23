package com.example.architectureproject;

import java.time.ZonedDateTime;

public class Events{
    String eventName;
    ZonedDateTime  eventDate;
    String description;
    String placement;
    Course course;
    int teacher_id;
    String time;
    int event_id;

    public Events(String eventName, ZonedDateTime eventDate, String description, String placement,Course course,String time) {
        this.time = time;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.description = description;
        this.placement = placement;
        this.course = course;
    }
    public Events(){};

    public String getEventName() {
        return eventName;
    }

    public ZonedDateTime getEventDate() {
        return eventDate;
    }

    public String getDescription() {
        return description;
    }

    public String getPlacement() {
        return placement;
    }
    public String getTime() {
        return time;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setEvent_id(int event_id){
        this.event_id = event_id;
    }

    public void setEventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
