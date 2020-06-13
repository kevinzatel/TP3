package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MusicianLandingActivity extends AppCompatActivity {

    DataBaseHelper db;
    Button viewProfile, findBanda, mySolicitudes, logOut;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_landing_page);

        user = (User) getIntent().getSerializableExtra("user");
        viewProfile = (Button) findViewById(R.id.btEditPerfil);
        findBanda = (Button) findViewById(R.id.btFindBanda);
        mySolicitudes = (Button) findViewById(R.id.btSolicitudes);
        logOut = (Button) findViewById(R.id.btLogout);

//        TextView welcomeText = (TextView) findViewById(R.id.welcomeTxt);
//        String userName = getIntent().getExtras().getString("userName");
//        welcomeText.append("Bienvenido " + userName);

        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewProfileIntent = new Intent(getApplicationContext(), MusicianProfileActivity.class);
                viewProfileIntent.putExtra("user", user);
                startActivity(viewProfileIntent);
            }
        });

        findBanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchBandIntent = new Intent(getApplicationContext(), MusicianBandSearchActivity.class);
                searchBandIntent.putExtra("user", user);
                startActivity(searchBandIntent);
            }
        });






    }
}