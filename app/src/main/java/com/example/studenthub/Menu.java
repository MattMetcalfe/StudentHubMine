package com.example.studenthub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Menu extends AppCompatActivity {
    private static Button button_dashboard;
    private static Button button_countdown;
    private static Button button_notes;
    private static Button button_scheduler;
    private static Button button_todo;
    private static Button button_signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        openCountdown();
        openNotes();
        openScheduler();
        openTodo();
    }


    public void openCountdown() {
        button_countdown = (Button) findViewById(R.id.countdownButton);
        button_countdown.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intentCountdown = new Intent("com.example.studenthub.Countdown");
                        startActivity(intentCountdown);
                    }
                }
        );
    }

    public void openNotes() {
        button_notes = (Button) findViewById(R.id.notesButton);
        button_notes.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intentNotes = new Intent("com.example.studenthub.NotePad");
                        startActivity(intentNotes);
                    }
                }
        );
    }

    public void openScheduler() {
        button_scheduler = (Button) findViewById(R.id.schedulerButton);
        button_scheduler.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intentScheduler = new Intent("com.example.studenthub.Scheduler");
                        startActivity(intentScheduler);
                    }
                }
        );
    }

    public void openTodo() {
        button_todo = (Button) findViewById(R.id.todoButton);
        button_todo.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intentTodo = new Intent("com.example.studenthub.Todo");
                        startActivity(intentTodo);
                    }
                }
        );
    }
}

