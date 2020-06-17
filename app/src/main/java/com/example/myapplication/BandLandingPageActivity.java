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
        final User updatedUser = (User) getIntent().getSerializableExtra("user");
        //final User updatedUser = db.getUser(user.getUserName());
        Button findMusicianBtn = findViewById(R.id.findMusicianBtn);
        Button editSearchBtn = findViewById(R.id.editSearchBtn);
        Button desactivateSearchBtn = findViewById(R.id.desactivateSearchBtn);
        Button editAccountBtn = findViewById(R.id.editAccountBtn);
        Button editProfileBtn = findViewById(R.id.editProfileBtn);
        TextView welcomeText = (TextView) findViewById(R.id.welcomeTxt);

        welcomeText.append(" " + updatedUser.getNickname());

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
                User user = db.getUser(updatedUser.getUserName());
                finish();
                Intent intent = new Intent(getApplicationContext(), BandLandingPageActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        editAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editAccountIntent = new Intent(getApplicationContext(), EditAccountActivity.class);
                editAccountIntent.putExtra("user", updatedUser);
                startActivity(editAccountIntent);
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(getApplicationContext(), EditProfileActivity.class);
                editProfileIntent.putExtra("user", updatedUser);
                startActivity(editProfileIntent);
            }
        });
    }

    private void goToSearchMusicianActivity(User user){
        Intent searchMusicianIntent = new Intent(getApplicationContext(), SearchMusicianActivity.class);
        searchMusicianIntent.putExtra("user", user);
        startActivity(searchMusicianIntent);
    }
}
