package com.example.cardiacrecorder;

import static java.sql.Types.NULL;

import java.io.Serializable;
import java.util.ArrayList;

public class Measurement implements Serializable {
    public static ArrayList<Measurement> measurementArrayList = new ArrayList<>();

    private int id;
    private String date;
    private String time;
    private String systolicPressure;
    private String diastolicPressure;
    private String heartRate;
    private String comment;

    public Measurement() {}

    public Measurement(String date, String time, String systolicPressure, String diastolicPressure, String heartRate, String comment) {
        this.date = date;
        this.time = time;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.heartRate = heartRate;
        this.comment = comment;
        this.id = NULL;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(String systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public String getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(String diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
