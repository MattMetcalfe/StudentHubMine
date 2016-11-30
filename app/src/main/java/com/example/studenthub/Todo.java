package com.example.studenthub;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fasterxml.jackson.core.format.MatchStrength;

import java.util.ArrayList;
import java.util.List;

public class Todo extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

     /*  final TaskDBHandler db = new TaskDBHandler(this);    //db = task database
       final List<Task> taskHistory = db.getAllTasks();     //taskHistory = list of all tasks in db
//TODO -- PRINT ALL TASKS FROM DATA BASE ON CREATE
        for (int t =0; t<taskHistory.size();t++){
            Task task = taskHistory.get(t);
            CheckBox chbx = new CheckBox(getApplicationContext());
            String inp = task.getLabel();
            chbx.setText(task.getLabel());
            // chbx.setText(String.valueOf(db.getAllTasks().size()));
            chbx.setChecked(false);
            chbx.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            //delete from db
                            db.deleteTask(task);
                            //remove from linear layout
                            linearlayout.removeView(chbx);
                        }
                    }
            );
            //chbx.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            chbx.setTextSize(25);
            chbx.setTextColor(Color.parseColor("#ffffff"));
            linearlayout.addView(chbx);
        }*/
//////////////////////////MASTER LINEAR LAYOUT////////////////////////
        final LinearLayout masterLinear = new LinearLayout(this);
        LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        masterLinear.setLayoutParams(mlp);
        masterLinear.setOrientation(LinearLayout.VERTICAL);
        masterLinear.setPadding(16, 16, 16, 16);
///////////////////////////INNER LINEAR LAYOUT/////////////////////////
        final LinearLayout linearlayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearlayout.setLayoutParams(lp);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setPadding(20, 170, 20, 40);//left top right bottom
////////////////////Scroll params...
        ViewGroup.LayoutParams scrollparams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
/////////////////////////////SCROLL VIEW//////////////////////////
        final ScrollView scrollview = new ScrollView(this);
        scrollview.setLayoutParams(scrollparams);
        scrollview.setFillViewport(true);
        scrollview.setBackgroundColor(Color.parseColor("#808fed"));
        linearlayout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        scrollview.addView(linearlayout);
///////////////////TITLE TextView//////////////////////////////////
        TextView title = new TextView(this);
        title.setText("TO DO LIST:");
        title.setTextSize(60);
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        title.setTextColor(Color.parseColor("#ffffff"));
////////////////////TASK INPUT EditText/////////////////////////////
        final EditText TaskInput = new EditText(this);
        TaskInput.setHint("click to enter new task");
        TaskInput.setTextColor(Color.parseColor("#ffffff"));
        TaskInput.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TaskInput.setTextSize(20);
//////////////////////////////PRINT ALL TASKS CURRENTLY IN DB/////////////////////////////
        final TaskDBHandler db = new TaskDBHandler(this);    //db = task database
        final List<Task> taskHistory = db.getAllTasks();     //taskHistory = list of all tasks in db
        //PRINT ALL TASKS FROM DATA BASE ON CREATE
        for (int t =0; t<taskHistory.size();t++){
            final Task dbTask = taskHistory.get(t);
            final CheckBox dbChbx = new CheckBox(getApplicationContext());
            String dbInp = dbTask.getLabel();
            dbChbx.setText(dbInp);
            dbChbx.setChecked(false);
            dbChbx.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            db.deleteTask(dbTask);
                            linearlayout.removeView(dbChbx);
                        }
                    }
            );
            //chbx.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dbChbx.setTextSize(25);
            dbChbx.setTextColor(Color.parseColor("#ffffff"));
            linearlayout.addView(dbChbx);
        }
////////////////////SAVE TASK Button////////////////////////////////
        Button AddButton = new Button(this);
        AddButton.setText("ADD TASK");

        AddButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        final CheckBox chbx = new CheckBox(getApplicationContext());
                        String inp = TaskInput.getText().toString();
                        final Task task = new Task(inp,taskHistory.size());
                        taskHistory.add(task);
                        db.addTask(task);
                        chbx.setText(task.getLabel());
                       // chbx.setText(String.valueOf(db.getAllTasks().size()));
                        chbx.setChecked(false);
                        chbx.setOnClickListener(
                                new View.OnClickListener(){
                                    public void onClick(View v){
                                        //delete from db
                                        db.deleteTask(task);
                                        //remove from linear layout
                                        linearlayout.removeView(chbx);
                                    }
                                }
                        );
                        //chbx.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        chbx.setTextSize(25);
                        chbx.setTextColor(Color.parseColor("#ffffff"));
                        linearlayout.addView(chbx);
                        TaskInput.setText("");
                    }
                }
        );
///////////////////ADD ELEMENTS TO MASTER LAYOUT//////////////////
        masterLinear.addView(title);
        masterLinear.addView(scrollview);
        masterLinear.addView(TaskInput);
        masterLinear.addView(AddButton);
        masterLinear.setBackgroundColor(Color.parseColor("#808fed"));
        setContentView(masterLinear);

    }
}
