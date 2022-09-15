package com.example.dz_v30.Models;

import java.util.ArrayList;

public class meds_list_model {
    private String id;
    private String time;
    private String medName;
    private String numOfTimes;
    private String state;
    private ArrayList<String> daysOfWeek;
    private String description;
    private String date_from;
    private String date_to;

    public meds_list_model(String id, String time, String medName, String numOfTimes, String state, ArrayList<String> daysOfWeek, String description, String date_from, String date_to) {
        this.id = id;
        this.time = time;
        this.medName = medName;
        this.numOfTimes = numOfTimes;
        this.state = state;
        this.daysOfWeek = daysOfWeek;
        this.description = description;
        this.date_from = date_from;
        this.date_to = date_to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getNumOfTimes() {
        return numOfTimes;
    }

    public void setNumOfTimes(String numOfTimes) {
        this.numOfTimes = numOfTimes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(ArrayList<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }
}

