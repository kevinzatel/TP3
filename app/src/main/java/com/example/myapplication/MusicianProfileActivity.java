package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MusicianProfileActivity extends AppCompatActivity {

    TextView nombre,instrumento;
    Button btEditProfile,btBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_profile);

        final User user = (User) getIntent().getSerializableExtra("user");

         nombre =(TextView) findViewById(R.id.msNombre);
         instrumento = (TextView) findViewById(R.id.msInstrumento);
         btEditProfile = (Button) findViewById(R.id.btEditPerfil);
         //btBack = (Button) findViewById(R.id.btBack);

        nombre.setText(user.getUserName());
        instrumento.setText(user.getInstrument());

        btEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(getApplicationContext(), MusicianEditProfileActivity.class);
                editProfileIntent.putExtra("user",user);
                startActivity(editProfileIntent);

            }
        });




    }



}


