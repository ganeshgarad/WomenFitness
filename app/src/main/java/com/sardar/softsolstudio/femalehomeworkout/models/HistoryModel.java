package com.sardar.softsolstudio.femalehomeworkout.models;

public class HistoryModel {
    public static final String TABLE_NAME = "histroytable";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE= "taskDate";
    public static final String COLUMN_TIME= "taskTime";


    private int id;
    private String daytitle;
    private String date;
    private String time;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_TIME + " TEXT"
                    + ")";


    public HistoryModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDaytitle() {
        return daytitle;
    }

    public void setDaytitle(String daytitle) {
        this.daytitle = daytitle;
    }

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
}
