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
    private DateTime start;
    private DateTime end;
    private String location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public DateTime getStart() {return start;}

    public void setStart(DateTime s) { this.start = s; }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime e) {
        this.end = e;
    }

    public String getLocation() { return location; }

    public void setLocation(String l) { this.location = l;}

    public String getTimeTill(){
        long SEC_DAYS = 1000*60*60*24;
        long SEC_HOURS = 1000*60*60;
        long SEC_MIN = 1000*60;
        long SEC_SEC = 1000;
        String timeTill = "";
        long miliSec = start.getValue();

        miliSec = miliSec - System.currentTimeMillis();
        int days = (int) (miliSec / (SEC_DAYS));
        if(days != 0){
            timeTill += days + " days ";
        }

        miliSec = miliSec - (days * (SEC_DAYS));
        int hours = (int) (miliSec / (SEC_HOURS));
        if(hours != 0){
            timeTill += hours + " hours ";
        }

        miliSec = miliSec - (hours * SEC_HOURS);
        int mins = (int) ((miliSec / SEC_MIN));
        if(mins != 0){
            timeTill += mins + " min ";
        }

        miliSec = miliSec - (mins * SEC_MIN);
        int secs = (int) (miliSec / SEC_SEC);
        if(secs != 0){
            timeTill += secs + " sec";
        }

        return(timeTill);
    }

}
