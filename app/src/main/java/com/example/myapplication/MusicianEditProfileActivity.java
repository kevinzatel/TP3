package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MusicianEditProfileActivity extends AppCompatActivity {
    DataBaseHelper db;
    EditText msNombre,msPassword,msPhone;
    Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_editprofile);
        db = new DataBaseHelper();

         final User user = (User) getIntent().getSerializableExtra("user");
         msNombre  = findViewById(R.id.msEditNombre);
         msPassword =  findViewById(R.id.msEditPassword);
         saveChanges = (Button) findViewById(R.id.btSaveChanges);
         msPhone =  findViewById(R.id.msPhone);
         User updatedUser = db.getUser(user.getUserName());

         msNombre.setText(updatedUser.getNickname());
         msPassword.setText(updatedUser.getPassword());
         msPhone.setText(updatedUser.getPhone());

        saveChanges.setOnClickListener(new View.OnClickListener() {
            private static final String TAG ="a" ;

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

            if(msNombre.getText().toString().equals("") || msPassword.getText().toString().equals("") || msPhone.getText().toString().equals("")) {
                Toast.makeText(MusicianEditProfileActivity.this, "Todos los campos deben estar completos", Toast.LENGTH_LONG).show();
            }
                else{
                        try {

                            user.setNickname(msNombre.getText().toString());
                            user.setPassword(msPassword.getText().toString());
                            user.setPhone(msPhone.getText().toString());

                            db.editMusicianProfile(user,msNombre.getText().toString(),msPassword.getText().toString(),msPhone.getText().toString());
                            User newUserValues = db.getUser(user.getUserName());
                            msNombre.setText(newUserValues.getNickname());
                            msPassword.setText(newUserValues.getPassword());
                            msPhone.setText(newUserValues.getPhone());
                            Toast.makeText(MusicianEditProfileActivity.this, "Cambios Realizados Exitosamente", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MusicianLandingActivity.class);
                            intent.putExtra("user", newUserValues);
                            startActivity(intent);


                        }
                        catch (Exception e){

                            Toast.makeText(MusicianEditProfileActivity.this, "Ocurrió un error interno. Intentá nuevamente", Toast.LENGTH_LONG).show();
                        }

                    }
            }
        });




    }
}
