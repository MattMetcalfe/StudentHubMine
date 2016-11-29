package com.example.studenthub;
import java.util.Calendar;

import java.util.UUID;

/**
 * Created by michaelspearing on 11/15/16.
 */

public class Note {
    //Note's private fields
    private String id;
    private String revisionTime;
    private String title;
    private String body;
    //Note constructors
    public Note(){
        id = UUID.randomUUID().toString();
        revisionTime = Calendar.getInstance().toString();
        title = "New Note";
        body = "";
    }

    public Note(String t){
        id = UUID.randomUUID().toString();
        revisionTime = Calendar.getInstance().toString();
        title = t;
        body = "";
    }

    public Note(String t, String b){
        id = UUID.randomUUID().toString();
        revisionTime = Calendar.getInstance().toString();
        title = t;
        body = b;
    }
    //Note setters
    public void setTime(String current){//cal.tostring format: Wed May 02 20:48:32 EEST 2012
        revisionTime = current;}
    public void setTitle(String t){
        title = t;
    }
    public void setBody(String b){
        body = b;
    }
    public void setId(String i){id = i;}
    //Note getters
    public String getId(){return id;}
    public String getTime(){
        return revisionTime;
    }
    public String getTitle(){
        return title;
    }
    public String getBody(){
        return body;
    }
}
