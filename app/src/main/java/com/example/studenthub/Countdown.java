package com.example.studenthub;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ScrollView;

import java.util.List;
// ToDo Make the call to the google API clear the previous one
public class Countdown extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_countdown);

        List<mEvent> mevents = Scheduler.getEvents();

        LinearLayout activityLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        activityLayout.setLayoutParams(lp);
        activityLayout.setOrientation(LinearLayout.VERTICAL);
        activityLayout.setPadding(16, 16, 16, 16);


        ViewGroup.LayoutParams tlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ScrollView sc = new ScrollView(this);
        sc.setLayoutParams(tlp);
        sc.setFillViewport(true);


        for (mEvent thisEvent :mevents){
            TextView dispText = new TextView(this);
            dispText.setLayoutParams(tlp);
            dispText.setPadding(16, 16, 16, 16);
            dispText.setVerticalScrollBarEnabled(true);
            dispText.setMovementMethod(new ScrollingMovementMethod());
            dispText.setText(thisEvent.getTitle() + "    " + (thisEvent.getStart()));
            activityLayout.addView(dispText);
        }
        sc.addView(activityLayout);
        setContentView(sc);
    }
}



