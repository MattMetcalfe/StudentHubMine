package com.example.studenthub;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Bailey on 11/15/2016.
 */
public interface ScheduleItem {

    //getters
    public String getTitle();
    public Date getDate();
    public Time getEnd();

    //setters
    public void setTitle(String t);
    public void setDate(Date d);
    public void setEnd(Time e);

}