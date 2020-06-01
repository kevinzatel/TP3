package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MusicianEditProfileActivity extends AppCompatActivity {
    DataBaseHelper db;
    EditText msNombre,msInstrumento,msPassword;
    Button saveChanges,back;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_editprofile);
        db = new DataBaseHelper();


        final User user = (User) getIntent().getSerializableExtra("user");
         msNombre  = (EditText) findViewById(R.id.msEditNombre);
         msInstrumento = (EditText) findViewById(R.id.msEditInstrumento);
         msPassword = (EditText) findViewById(R.id.msEditPassword);
         saveChanges = (Button) findViewById(R.id.btSaveChanges);
         back = (Button) findViewById(R.id.btBack);

        msNombre.setText(user.getUserName());
        msInstrumento.setText(user.getInstrument());
        //msPassword.setText(user.getPassword());

        /*METODO PARA ACTUALIZAR*/

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*VOLVER A ACTIVITY ANTERIOR*/

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}
