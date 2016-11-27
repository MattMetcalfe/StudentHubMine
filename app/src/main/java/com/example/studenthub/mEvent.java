package com.example.studenthub;

import java.sql.Time;
import java.util.Date;

/**
 * Created by michaelspearing on 11/15/16.
 */

public class mEvent implements ScheduleItem {
    //Events are created in the calendar and will implement Google Calendar API
    //Events are displayed in Scheduler
    //Making sure we are on the same page.

    private String title;
    private Date date;
    private Time end;
    private Time start;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public Time getStart() {return start; }

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

    public void setStart(Time s) { this.start = s; }

    @Override
    public void setEnd(Time e) {
        this.end =e;
    }
}
