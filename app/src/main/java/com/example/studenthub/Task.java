package com.example.studenthub;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bailey on 11/15/2016.
 */
public class Task{
    //task private fields
    private String id;
    private String label;
    private String timeStamp;
    //task constructors
    public Task(){
        id=null;
        label="";
        timeStamp= Calendar.getInstance().toString();
    }
    public Task(String lab, String rownum){
        id=rownum;
        label=lab;
        timeStamp =Calendar.getInstance().toString();
    }
    public Task(String lab, int rownum){
        id=String.valueOf(rownum);
        label= lab;
        timeStamp = Calendar.getInstance().toString();
    }
    //task getters
    public String getId(){return id;}
    public String getLabel() {
        return label;
    }
    public String getTime() {return timeStamp;}
    //task setters
    public void setId(String i){this.id=i;}
    public void setId(int i){this.id=String.valueOf(i);}
    public void setLabel(String l) {this.label = l;}
    public void setTime(String t){this.timeStamp = t;}
}