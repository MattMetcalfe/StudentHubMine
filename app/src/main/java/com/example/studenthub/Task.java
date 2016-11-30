package com.example.studenthub;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Bailey on 11/15/2016.
 */
public class Task{
    //task private fields
    private String id;
    private String label;
    //task constructors
    public Task(){
        id=null;
        label="";
    }
    public Task(String lab, String rownum){
        id=rownum;
        label=lab;
    }
    public Task(String lab, int rownum){
        id=String.valueOf(rownum);
        label= lab;
    }
    //task getters
    public String getId(){return id;}
    public String getLabel() {
        return label;
    }
    //task setters
    public void setId(String i){this.id=i;}
    public void setId(int i){this.id=String.valueOf(i);}
    public void setLabel(String l) {this.label = l;}
}