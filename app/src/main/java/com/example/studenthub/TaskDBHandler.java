package com.example.studenthub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bailey on 11/29/2016.
 */
public class TaskDBHandler extends SQLiteOpenHelper{

    // Database Version, DB Name, Table Name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TaskDB";
    private static final String TABLE_NAME = "allTasks";
    // Task Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LABEL = "label";
    private static final String KEY_TIME = "time";

    public TaskDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE_COMMAND= "CREATE TABLE " + TABLE_NAME +" ( "
                +KEY_ID+" TEXT, "
                +KEY_LABEL+" TEXT, "
                +KEY_TIME+" TEXT"
                +" )";
        db.execSQL(CREATE_TABLE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //Add Task to DB
    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getId());
        values.put(KEY_LABEL, task.getLabel());
        values.put(KEY_TIME, task.getTime());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    //Update Task in DB (depn. on id)
    public void updateTask(Task task){//needs to depend on row num
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,task.getId());
        values.put(KEY_LABEL,task.getLabel());
        values.put(KEY_TIME, task.getTime());
        db.update(TABLE_NAME, values, KEY_ID+" = ?",
                new String[] { String.valueOf(task.getId()) });
    }
    //Delete Task from DB, (depn. on id)
    public void deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
        taskIndexReset();
        db.close();
    }
    //Read All (return list)
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        String queryCommand = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryCommand, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getString(0));
                task.setLabel(cursor.getString(1));
                task.setTime(cursor.getString(2));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }
    //Read Task from DB, given row number
    public Task getTask(Long row_num) {
        List<Task> taskList = new ArrayList<Task>();
        Task result = new Task();
        Integer r = row_num.intValue();
        taskList = getAllTasks();
        result = taskList.get(r);
        return result;
    }

    //Reset ID indices (use after deletion)
    public void taskIndexReset(){
        String queryCommand = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryCommand, null);
        int i =0;
        if (cursor.moveToFirst()) {
            do {//id, label, time
                ContentValues values = new ContentValues();
                String oldid=cursor.getString(0);
                values.put(KEY_ID,String.valueOf(i));
                i++;
                values.put(KEY_LABEL, cursor.getString(1));
                String timestamp = cursor.getString(2);
                values.put(KEY_TIME, timestamp);
                db.update(TABLE_NAME, values, KEY_TIME+" = ?",
                        new String[] { String.valueOf(timestamp) });
            } while (cursor.moveToNext());
        }
    }


}
