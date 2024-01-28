package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {


    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = findViewById(R.id.progress);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();



    }


    private void doWork(){

        for(int i=0; i<100; i+=10){
            try{
                Thread.sleep(300);
                pb.setProgress(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    private void startApp(){

        Intent i = new Intent(MainActivity.this,login.class);
        startActivity(i);
    }

}