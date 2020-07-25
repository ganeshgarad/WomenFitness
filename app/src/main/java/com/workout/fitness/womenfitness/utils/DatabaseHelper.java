package com.workout.fitness.womenfitness.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.workout.fitness.womenfitness.models.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "history_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(HistoryModel.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + HistoryModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String taskTitle, String taskDate) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(HistoryModel.COLUMN_TITLE, taskTitle);
        values.put(HistoryModel.COLUMN_DATE, taskDate);
        // values.put(HistoryModel.COLUMN_TIME, taskTime);

        // insert row
        long id = db.insert(HistoryModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public List<HistoryModel> getdateEvent(String date) {
        List<HistoryModel> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + HistoryModel.TABLE_NAME + " WHERE " +
                HistoryModel.COLUMN_DATE +" = '" + date + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HistoryModel model=new HistoryModel();
                model.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_ID))));
                model.setDaytitle(cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_TITLE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_DATE)));
                model.setTime(cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_TIME)));
                //note.setAlarm(cursor.getString(cursor.getColumnIndex(TaskModel.COLUMN_ALARM)));

                notes.add(model);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public List<HistoryModel> getAllNotes() {
        List<HistoryModel> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + HistoryModel.TABLE_NAME + " ORDER BY " +
                HistoryModel.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HistoryModel model=new HistoryModel();
                model.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_ID))));
                model.setDaytitle(cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_TITLE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_DATE)));
                model.setTime(cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_TIME)));

                notes.add(model);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + HistoryModel.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


    public void deleteNote(HistoryModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HistoryModel.TABLE_NAME, HistoryModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(HistoryModel.TABLE_NAME, null, null);
        db.close();
    }
}