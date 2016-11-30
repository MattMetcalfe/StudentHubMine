package com.example.studenthub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// ToDo Make the call to the google API clear the previous one
public class Countdown extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_countdown);

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


        Button mRefreshButton = new Button(this);
        mRefreshButton.setText("Click to Refresh");
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // TODO try and make this update the data
                startActivity(getIntent());
            }
        });
        pageLayout.addView(mRefreshButton);

        ScrollView sc = new ScrollView(this);
        sc.setLayoutParams(tlp);
        sc.setFillViewport(true);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(lp);
        tableLayout.setOrientation(LinearLayout.VERTICAL);
        tableLayout.setPadding(16, 16, 16, 16);

        /*
        LinearLayout activityLayout = new LinearLayout(this);
        activityLayout.setLayoutParams(lp);
        activityLayout.setOrientation(LinearLayout.VERTICAL);
        activityLayout.setPadding(16, 16, 16, 16);*/

        for (mEvent thisEvent : mevents) {
            TableRow row = new TableRow(this);
            TextView dispText = new TextView(this);
            //dispText.setLayoutParams(tlp);
            //dispText.setPadding(16, 16, 16, 16);
            //dispText.setVerticalScrollBarEnabled(true);
            //dispText.setMovementMethod(new ScrollingMovementMethod());
            dispText.setText(thisEvent.getTitle());
            if(dispText.getText().length() > 30){
                String cut = (String)dispText.getText();
                cut = cut.substring(0,29);
                dispText.setText(cut);
            }
            //activityLayout.addView(dispText);
            row.addView(dispText);

            TextView dispVal = new TextView(this);
            //dispVal.setLayoutParams(tlp);
            dispVal.setGravity(Gravity.RIGHT);
            //dispVal.setPadding(16, 16, 16, 16);
            //dispText.setVerticalScrollBarEnabled(true);
            //dispText.setMovementMethod(new ScrollingMovementMethod());
            dispVal.setText(thisEvent.getTimeTill());
            //activityLayout.addView(dispText);
            row.addView(dispVal);
            tableLayout.addView(row);
        }

        sc.addView(tableLayout);
        pageLayout.addView(sc);
        setContentView(pageLayout);

    }
}