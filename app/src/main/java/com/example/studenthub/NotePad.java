package com.example.studenthub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NotePad extends AppCompatActivity {

    static boolean underEdit = false;
    static int rowUnderEdit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);


        final NoteDBHandler db = new NoteDBHandler(this);                       //db = note database
        final Button save_note = (Button)findViewById(R.id.SaveNoteButton);     //save_note = save button (widget)
        final Button delete_note = (Button)findViewById(R.id.DeleteNoteButton); //delete_note = delete button (widget)
        final ArrayList navItems = new ArrayList<String>();                     //navItems = list
        final ListView note_nav = (ListView)findViewById(R.id.NoteNavigation);  //note_nav = listView side bar (widget)
        final ArrayAdapter adapter = new ArrayAdapter<String>                   //adapter = adapter to update note nav bar
                (this, android.R.layout.simple_list_item_1, navItems);
        navItems.add("MY NOTES:");
        navItems.add("   NEW");
        /// add all from data base
        List<Note> noteHistory = new ArrayList<Note>();
        noteHistory = db.getAllNotes();
        for (int n = 0; n < noteHistory.size(); n++) {
            navItems.add(noteHistory.get(n).getTitle());
        }
        //register the navItem adapter to the note_nav
        note_nav.setAdapter(adapter);
/////////////////////////////////////save_button handler/////////////////////////////////////////
        if (save_note != null) {
            save_note.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            EditText titleBox = (EditText) findViewById(R.id.titleInput);
                            EditText bodyBox = (EditText) findViewById(R.id.noteInput);
                            String title = titleBox.getText().toString();
                            String body = bodyBox.getText().toString();
                            if (underEdit == false) {   //this is a brand new note
                                Note note = new Note(title, body, (navItems.size())-2);
                                db.addNote(note);
                                navItems.add(title);

                                List<Note> all1 = new ArrayList<Note>();
                                all1 = db.getAllNotes();
                                String print1="";
                                for(int i =0; i< all1.size();i++){
                                    print1 += all1.get(i).getId();
                                }
                                db.indexReset();
                                List<Note> all2 = new ArrayList<Note>();
                                all2 = db.getAllNotes();
                                String print2="";
                                for(int i =0; i< all2.size();i++){
                                    print2 += all2.get(i).getId();
                                }
                                bodyBox.setText(print1 +"/n/n"+print2);


                                underEdit=true;
                                rowUnderEdit = (navItems.size())-2;
                                adapter.notifyDataSetChanged();
                            }
                            else{   //an existing note is being edited
                                Note note = new Note(title, body, rowUnderEdit);
                                db.updateNote(note);
                                navItems.set(rowUnderEdit+2, title);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
            );
        }
        else{
            EditText titleBox = (EditText)findViewById(R.id.titleInput);
            char[] error = "Save ERROR".toCharArray();
            titleBox.setText(error, 0, error.length);
        }
/////////////////////////////////////delete button handler//////////////////////////////////
        if (delete_note != null) {
            delete_note.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            EditText titleBox = (EditText) findViewById(R.id.titleInput);
                            EditText bodyBox = (EditText) findViewById(R.id.noteInput);
                            String title = titleBox.getText().toString();
                            String body = bodyBox.getText().toString();
                            if (underEdit == true) {   //ONLY delete if this is an existing note
                                Note note = new Note(title, body, rowUnderEdit);
                                db.deleteNote(note);
                                navItems.remove(rowUnderEdit+2);
                                adapter.notifyDataSetChanged();
                                //clear note pad
                                //reset rowunderedit int and underedit flag
                                titleBox.setText("");
                                bodyBox.setText("");
                                underEdit=false;
                                rowUnderEdit=0;
                            }
                        }
                    }
            );
        }
        else{
            EditText titleBox = (EditText)findViewById(R.id.titleInput);
            char[] error = "Delete ERROR".toCharArray();
            titleBox.setText(error, 0, error.length);
        }
//////////////////////////////////////note_nav handler//////////////////////////////////////
        note_nav.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                EditText titleBox = (EditText)findViewById(R.id.titleInput);
                EditText bodyBox = (EditText)findViewById(R.id.noteInput);
                //ID = ROW OF NOTE IN DATABASE +2 (id-2 = row in db)
                //read note from database then display its title and body
                //need a db method that returns note GIVEN the row number
                if(id>=2) {
                    Note note = db.getNote(id - 2);
                    char[] t = note.getTitle().toCharArray();
                    titleBox.setText(t, 0, t.length);
                    char[] b = note.getBody().toCharArray();
                    bodyBox.setText(b, 0, b.length);
                    underEdit= true;
                    Long l= id;
                    rowUnderEdit = (l.intValue()) -2;
                }
                else if (id==1){// therefore, clicked "new"
                    char[] t = "".toCharArray();
                    titleBox.setText(t, 0, t.length);
                    char[] b = "".toCharArray();
                    bodyBox.setText(b, 0, b.length);
                    underEdit= false;
                }
                //else id==0 which is a placeholder -- do nothing.
            }
        });
    }
}
