package com.example.studenthub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ScrollView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Countdown extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setting up the
        List<mEvent> mevents = Scheduler.getEvents();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        ViewGroup.LayoutParams tlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout pageLayout = new LinearLayout(this);
        pageLayout.setLayoutParams(lp);
        pageLayout.setOrientation(LinearLayout.VERTICAL);
        pageLayout.setPadding(16, 16, 16, 16);

        TextView title = new TextView(this);
        title.setTextSize(60);
        title.setTextColor(Color.parseColor("#FFFFFF"));
        title.setText("COUNTDOWN");
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView noEvents = new TextView(this);
        noEvents.setTextSize(30);
        noEvents.setTextColor(Color.parseColor("#FFFFFF"));
        noEvents.setText("\n\n\nNo Events to Track");
        noEvents.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);



        Button openScheduler = new Button(this);
        openScheduler.setText("Make sure your calendar is initiated");
        openScheduler.setBackgroundColor(Color.parseColor("#CC5500"));
        openScheduler.setTextColor(Color.parseColor("#FFFFFF"));
        final Intent scheduler_Intent = new Intent(this, Scheduler.class);
        openScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(scheduler_Intent));
            }
        });

        Button mRefreshButton = new Button(this);
        mRefreshButton.setText("Click to Update");
        mRefreshButton.setBackgroundColor(Color.parseColor("#CC5500"));
        mRefreshButton.setTextColor(Color.parseColor("#FFFFFF"));
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // TODO try and make this update the data
                startActivity(getIntent());
            }
        });

        ScrollView sc = new ScrollView(this);
        sc.setLayoutParams(tlp);
        sc.setFillViewport(true);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(lp);
        tableLayout.setOrientation(LinearLayout.VERTICAL);
        tableLayout.setPadding(16, 16, 16, 16);


        for (mEvent thisEvent : mevents) {
            if (thisEvent.getTimeTill().equals("")){
            continue;
        }
            TableRow row = new TableRow(this);
            TextView dispText = new TextView(this);

            dispText.setText(thisEvent.getTitle());
            if(dispText.getText().length() > 30) {
                String cut = (String) dispText.getText();
                cut = cut.substring(0, 28);
                dispText.setText(cut);
            }
            dispText.setTextColor(Color.parseColor("#FFFFFF"));
            dispText.setTextSize(20);
            row.addView(dispText);

            TextView dispVal = new TextView(this);
            dispVal.setGravity(Gravity.RIGHT);
            dispVal.setText(thisEvent.getTimeTill());

            dispVal.setTextColor(getUrgencyColor(thisEvent.getTimeTill()));
            dispVal.setTextSize(20);
            row.addView(dispVal);
            tableLayout.addView(row);
        }

        pageLayout.addView(title);
        if(mevents.size() == 0){
            pageLayout.addView(openScheduler);
            pageLayout.addView((noEvents));

        } else {
            pageLayout.addView(mRefreshButton);
        }

        sc.addView(tableLayout);
        pageLayout.addView(sc);
        pageLayout.setBackgroundColor(Color.parseColor("#808fed"));
        setContentView(pageLayout);

    }

    private int getUrgencyColor(String timeTill){
        if(timeTill.contains("d")){
            return(Color.parseColor("#00FF00"));
        }
        else if( timeTill.contains("h")){
            return(Color.parseColor("#FFFF00"));
        }
        else{
            return(Color.parseColor("#FF0000"));
        }
    }
}