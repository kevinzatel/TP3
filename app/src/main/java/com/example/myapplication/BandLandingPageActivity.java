package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BandLandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_landing_page);

        final User user = (User) getIntent().getSerializableExtra("user");
        Button findMusicianBtn = findViewById(R.id.findMusicianBtn);
        Button goToProfileBtn = findViewById(R.id.goToProfileBtn);
        TextView welcomeText = (TextView) findViewById(R.id.welcomeTxt);

        welcomeText.append(" " + user.getUserName());

        findMusicianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchMusicianIntent = new Intent(getApplicationContext(), SearchMusicianActivity.class);
                searchMusicianIntent.putExtra("user", user);
                startActivity(searchMusicianIntent);
            }
        });

        goToProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bandProfileIntent = new Intent(getApplicationContext(), BandProfileActivity.class);
                bandProfileIntent.putExtra("user", user);
                startActivity(bandProfileIntent);
            }
        });

    }
}
