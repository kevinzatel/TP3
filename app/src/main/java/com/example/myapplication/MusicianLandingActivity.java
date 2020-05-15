package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MusicianLandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_landing_page);

        if(getIntent().hasExtra("userName")){
            TextView welcomeText = (TextView) findViewById(R.id.welcomeTxt);
            String userName = getIntent().getExtras().getString("userName");
            welcomeText.append(" " + userName);
        }
    }
}