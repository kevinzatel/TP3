package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MusicianProfileActivity extends AppCompatActivity {

    TextView nombre,phone,username,welcomeTxt;
    Button btEditProfile,btBack;
    User updatedUser;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_musician_profile);
         db = new DataBaseHelper();

         final User user = (User) getIntent().getSerializableExtra("user");

         nombre =(TextView) findViewById(R.id.msNombre);
         btEditProfile = (Button) findViewById(R.id.btEditPerfil);
         phone= findViewById(R.id.msPhone);
         username=findViewById(R.id.msUsername);
         welcomeTxt=findViewById(R.id.welcomeTxt);
         updatedUser = db.getUser(user.getUserName());

        welcomeTxt.append("Bienvenido :)");
        nombre.setText(updatedUser.getNickname());
        phone.setText(updatedUser.getPhone());
        username.setText(updatedUser.getUserName());

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


