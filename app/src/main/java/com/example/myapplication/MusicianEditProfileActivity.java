package com.example.myapplication;

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


         msNombre.setText(user.getNickname());
         msPassword.setText(user.getPassword());
         msPhone.setText(user.getPhone());

        /*METODO PARA ACTUALIZAR*/

        saveChanges.setOnClickListener(new View.OnClickListener() {
            private static final String TAG ="a" ;

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
               /*validar vacios*/
            if(msNombre.getText().toString().equals("") | msPassword.getText().toString().equals("")
                    | msPhone.getText().toString().equals("")) {


                Toast.makeText(MusicianEditProfileActivity.this, "Todos los campos deben estar completos.", Toast.LENGTH_LONG).show();
            }
                else{
                        try {


                            user.setNickname(msNombre.getText().toString());
                            user.setPassword(msPassword.getText().toString());
                            user.setPhone(msPhone.getText().toString());

                            db.editMusicianProfile(user,msNombre.getText().toString(),msPassword.getText().toString(),msPhone.getText().toString()


                                                    );
                            Toast.makeText(MusicianEditProfileActivity.this, "Cambios Realizados Exitosamente", Toast.LENGTH_LONG).show();

                        }
                        catch (Exception e){

                            Toast.makeText(MusicianEditProfileActivity.this, "Ocurrió un error interno. Intentá nuevamente", Toast.LENGTH_LONG).show();
                        }

                    }
            }
        });




    }
}
