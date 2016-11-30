package com.example.studenthub;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public TaskDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE_COMMAND= "CREATE TABLE " + TABLE_NAME +" ( "
                +KEY_ID+" TEXT, "
                +KEY_LABEL+" TEXT" +" )";
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
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //Update Task in DB, given...

    //Read Task from DB, given...

    //Read All (return list)

    //Delete Task from DB, given...

}
