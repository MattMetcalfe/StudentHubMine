package com.example.studenthub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;

/**
 * Created by michaelspearing on 11/29/16.
 */

public class Landing extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // Button Listeners
        findViewById(R.id.menu_button).setOnClickListener(this);

    }

    private void navigateTo(String activity){
        Intent intent = new Intent("com.example.studenthub." + activity);
        startActivity(intent);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.menu_button:
                navigateTo("Menu");
                break;
        }

    }

}
