package com.example.dz_v30.Models;

import java.util.ArrayList;

public class time_model {
    private String time;
    private String note;
    private ArrayList<String> med;

    public time_model(String time, String note, ArrayList<String> med) {
        this.time = time;
        this.note = note;
        this.med = med;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<String> getMed() {
        return med;
    }

    public void setMed(ArrayList<String> med) {
        this.med = med;
    }
}
