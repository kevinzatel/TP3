package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MusicianLandingActivity extends AppCompatActivity {

    Button viewProfile, findBanda, mySolicitudes;
    TextView welcometxt;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_landing_page);

        viewProfile = (Button) findViewById(R.id.btEditPerfil);
        findBanda = (Button) findViewById(R.id.btFindBanda);
        mySolicitudes = (Button) findViewById(R.id.btSolicitudes);
        welcometxt = (TextView) findViewById(R.id.welcomeTxt);
        user = (User) getIntent().getSerializableExtra("user");

        welcometxt.setText("Bienvenido " + user.getNickname() + " !");


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
                Intent findBandIntent = new Intent(getApplicationContext(), MusicianBandSearchActivity.class);
                findBandIntent.putExtra("user", user);
                startActivity(findBandIntent);
            }
        });

    }
}