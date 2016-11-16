package com.example.studenthub;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Bailey on 11/15/2016.
 */
public interface ScheduleItem {

    //getters
    String getTitle();
    Date getDate();
    Time getEnd();

    //setters
    void setTitle(String t);
    void setDate(Date d);
    void setEnd(Time e);

}