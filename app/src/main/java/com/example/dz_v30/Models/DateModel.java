package com.example.dz_v30.Models;

public class DateModel {
    String dateOfMonth;
    String month;
    String year;

    public DateModel(String dateOfMonth, String month, String year) {
        this.dateOfMonth = dateOfMonth;
        this.month = month;
        this.year = year;
    }

    public String getDateOfMonth() {
        return dateOfMonth;
    }

    public void setDateOfMonth(String dateOfMonth) {
        this.dateOfMonth = dateOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
