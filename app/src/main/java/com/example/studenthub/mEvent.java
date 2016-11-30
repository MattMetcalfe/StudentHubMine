package com.example.studenthub;

import java.sql.Time;
import java.util.Date;
import com.google.api.client.util.DateTime;

/**
 * Created by michaelspearing on 11/15/16.
 */

public class mEvent {
    //Events are created in the calendar and will implement Google Calendar API
    //Events are displayed in Scheduler
    //Making sure we are on the same page.

    private String title;
    private Date date;
    private Time end;
    private Time start;
    private String location;

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Time getEnd() {
        return end;
    }

    public Time getStart() {return start; }

    public String getLocation() { return location; }



    public void setTitle(String t) {
        this.title = t;
    }

    public void setDate(Date d) { this.date =d;}

    public void setEnd(Time e) { this.end =e;}

    public void setLocation(String l) { this.location = l;}

    public void setStart(Time s) { this.start = s; }




}
