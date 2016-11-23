package com.example.studenthub;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Bailey on 11/15/2016.
 */
public class Task implements ScheduleItem{
    //Tasks are created in To Do list
    //Tasks are displayed in Countdown

    private String title;
    private Date date;
    private Time end;
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public Date getDate() {
        return date;
    }
    @Override
    public Time getEnd() {
        return end;
    }

    @Override
    public void setTitle(String t) {
        this.title = t;
    }
    @Override
    public void setDate(Date d) {
        this.date =d;
    }
    @Override
    public void setEnd(Time e) {
        this.end =e;
    }
}