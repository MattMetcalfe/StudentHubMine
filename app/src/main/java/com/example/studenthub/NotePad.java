package com.example.studenthub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NotePad extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        // Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // setSupportActionBar(myToolbar);

        //import view button and edit text (3)
        final NoteDBHandler db = new NoteDBHandler(this);                       //db = note database
        final Button save_note = (Button)findViewById(R.id.SaveNoteButton);     //save_note = save button (widget)
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

        if (save_note != null) {
            save_note.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            EditText titleBox = (EditText)findViewById(R.id.titleInput);
                            EditText bodyBox = (EditText)findViewById(R.id.noteInput);
                            String title = titleBox.getText().toString();
                            String body = bodyBox.getText().toString();
                            //save whatever was in text box into db
                            Note note = new Note(title, body);
                            db.addNote(note);
                            //add to note nav bar
                            navItems.add(title);
                            adapter.notifyDataSetChanged();
                        }
                    }
            );
        }
        else{
            EditText titleBox = (EditText)findViewById(R.id.titleInput);
            char[] error = "NULL ERROR".toCharArray();
            titleBox.setText(error, 0, error.length);
        }

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
                }
                else if (id==1){// therefore, clicked "new"
                    char[] t = "".toCharArray();
                    titleBox.setText(t, 0, t.length);
                    char[] b = "".toCharArray();
                    bodyBox.setText(b, 0, b.length);
                }
                //else id==0 which is a placeholder -- do nothing.
            }
        });
    }
}
