package com.example.studenthub;
import java.util.Calendar;

import java.util.UUID;

/**
 * Created by michaelspearing on 11/15/16.
 */

public class Note {
    //Note's private fields
    private String id;
    private Calendar revisionTime;
    private String title;
    private String body;
    //Note constructors
    public Note(){
        id = UUID.randomUUID().toString();
        revisionTime = Calendar.getInstance();
        title = "New Note";
        body = "";
    }

    public Note(String t){
        id = UUID.randomUUID().toString();
        revisionTime = Calendar.getInstance();
        title = t;
        body = "";
    }

    public Note(String t, String b){
        id = UUID.randomUUID().toString();
        revisionTime = Calendar.getInstance();
        title = t;
        body = b;
    }
    //Note setters
    public void updateTime(Calendar current){
        revisionTime = current;
    }
    public void setTitle(String t){
        title = t;
    }
    public void setBody(String b){
        body = b;
    }
    //Note getters
    public String getId(){
        return id;
    }
    public Calendar getTime(){
        return revisionTime;
    }
    public String getTitle(){
        return title;
    }
    public String getBody(){
        return body;
    }
}
