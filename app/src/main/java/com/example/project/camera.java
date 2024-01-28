package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class camera extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    public void profileF(View v){

        Intent i = new Intent(camera.this,profile.class);
        startActivity(i);
    }

    public void changeF(View v){

        Intent i = new Intent(camera.this,blackandwhite.class);
        startActivity(i);
    }
    public void trackF(View v){

        Intent i = new Intent(camera.this,track.class);
        startActivity(i);
    }

    public void logoutF(View v){

        Intent i = new Intent(camera.this,login.class);
        startActivity(i);
        finish();
    }
}