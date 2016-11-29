package com.example.studenthub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.math.*;

/**
 * Created by Bailey on 11/27/2016.
 */
public class NoteDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME="NoteDB";
    private static final String TABLE_NAME="allNotes";
    //table columns
    private static final String KEY_ID = "id";
    private static final String KEY_TIME="time";
    private static final String KEY_TITLE="title";
    private static final String KEY_BODY="body";

    public NoteDBHandler(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE_COMMAND= "CREATE TABLE " + TABLE_NAME +" ( "
                +KEY_ID+" TEXT, "
                +KEY_TIME+" TEXT, "
                +KEY_TITLE+" TEXT, "
                +KEY_BODY+" TEXT" +" )";

        db.execSQL(CREATE_TABLE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //if old table exists, drop it
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //re create
        onCreate(db);
    }

    public void addNote(Note note){
        //create entry
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, note.getId());
        values.put(KEY_TIME, note.getTime());   //time of last revision
        //cal.tostring format: Wed May 02 20:48:32 EEST 2012
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_BODY, note.getBody());
        //insert entry as new row in master table
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //create update entry
        values.put(KEY_TIME, note.getTime());
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_BODY,note.getBody());
        //update old row in master table
        return ( db.update(TABLE_NAME, values, KEY_ID+" = ?"
                , new String[]{String.valueOf(note.getId())}) ) >0;
    }

    public void deleteNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID+" = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<Note>();
        // Create Query Command
        String queryCommand = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryCommand, null);
        // Loop through all rows and populate noteList
        if (cursor.moveToFirst()) {
            do {
                //cols: id, time, title, body
                Note note = new Note();
                note.setId(cursor.getString(0));
                note.setTime(cursor.getString(1));
                note.setTitle(cursor.getString(2));
                note.setBody(cursor.getString(3));
                // Adding note to note list
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        // return contact list
        return noteList;
    }

    public Note getNote(Long row_num) {
        List<Note> noteList = new ArrayList<Note>();
        Note result = new Note();

        Integer r = row_num.intValue();

        noteList = getAllNotes();
        result = noteList.get(r);

        return result;
    }
}
