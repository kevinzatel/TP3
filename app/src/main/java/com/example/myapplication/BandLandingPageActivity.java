package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BandLandingPageActivity extends AppCompatActivity {
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_landing_page);

        db = new DataBaseHelper();
        final User user = (User) getIntent().getSerializableExtra("user");
        final User updatedUser = db.getUser(user.getUserName());
        Button findMusicianBtn = findViewById(R.id.findMusicianBtn);
        Button editSearchBtn = findViewById(R.id.editSearchBtn);
        Button desactivateSearchBtn = findViewById(R.id.desactivateSearchBtn);
        Button goToProfileBtn = findViewById(R.id.goToProfileBtn);
        TextView welcomeText = (TextView) findViewById(R.id.welcomeTxt);

        welcomeText.append(" " + user.getUserName());

        if(updatedUser.getMusicianSearching() == null){
            editSearchBtn.setVisibility(View.GONE);
            desactivateSearchBtn.setVisibility(View.GONE);
            findMusicianBtn.setVisibility(View.VISIBLE);
        }
        else{
            editSearchBtn.setVisibility(View.VISIBLE);
            desactivateSearchBtn.setVisibility(View.VISIBLE);
            findMusicianBtn.setVisibility(View.GONE);
        }

        findMusicianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchMusicianActivity(updatedUser);
            }
        });

        editSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchMusicianActivity(updatedUser);
            }
        });

        desactivateSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.desactivateMusicianSearch(updatedUser);
                finish();
                Intent intent = getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        goToProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bandProfileIntent = new Intent(getApplicationContext(), BandProfileActivity.class);
                bandProfileIntent.putExtra("user", updatedUser);
                startActivity(bandProfileIntent);
            }
        });

    }

    private void goToSearchMusicianActivity(User user){
        Intent searchMusicianIntent = new Intent(getApplicationContext(), SearchMusicianActivity.class);
        searchMusicianIntent.putExtra("user", user);
        startActivity(searchMusicianIntent);
    }
}
