package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MusicianEditProfileActivity extends AppCompatActivity {
    DataBaseHelper db;
    EditText msNombre,msInstrumento,msPassword;
    Spinner spInstrumento;
    Button saveChanges,btBack;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_editprofile);
        db = new DataBaseHelper();


        final User user = (User) getIntent().getSerializableExtra("user");
         msNombre  = (EditText) findViewById(R.id.msEditNombre);
         //msInstrumento = (EditText) findViewById(R.id.msEditInstrumento);
         spInstrumento = (Spinner) findViewById(R.id.instrumentoSpinner);
         msPassword = (EditText) findViewById(R.id.msEditPassword);
         saveChanges = (Button) findViewById(R.id.btSaveChanges);
         btBack = (Button) findViewById(R.id.btBack);

        String userInstrument = user.getInstrument();
        ArrayAdapter<CharSequence> adapterInstrument = ArrayAdapter.createFromResource(this, R.array.instrumentos, android.R.layout.simple_spinner_item);
        adapterInstrument.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInstrumento.setAdapter(adapterInstrument);
        if (userInstrument != null) {
            int spinnerPosition = adapterInstrument.getPosition(userInstrument);
            spInstrumento.setSelection(spinnerPosition);
        }
         msNombre.setText(user.getUserName());
//      spInstrumento.setText(user.getInstrument());
        msPassword.setText(user.getPassword());



        /*METODO PARA ACTUALIZAR*/

        saveChanges.setOnClickListener(new View.OnClickListener() {
            private static final String TAG ="a" ;

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
               /*validar vacios*/
            if(msNombre.getText().toString().equals("") | msPassword.getText().toString().equals("")) {
                Toast.makeText(MusicianEditProfileActivity.this, "Todos los campos deben estar completos.", Toast.LENGTH_LONG).show();
            }
                else{
                        try {

                            //db.editMusicianProfile(user,msNombre.getText().toString(),msInstrumento.getText().toString(),msPassword.getText().toString());
                            db.editMusicianProfile(user,msNombre.getText().toString(),spInstrumento.getSelectedItem().toString(),msPassword.getText().toString());
                            Toast.makeText(MusicianEditProfileActivity.this, "Cambios realizadosss.", Toast.LENGTH_LONG).show();

                        }
                        catch (Exception e){

                            Toast.makeText(MusicianEditProfileActivity.this, "Error en conexión con base de datos.", Toast.LENGTH_LONG).show();
                        }



                    }
            }
        });

        /*VOLVER A ACTIVITY ANTERIOR*/

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //onBackPressed();
                        Intent backIntent = new Intent(getApplicationContext(),MusicianProfileActivity.class);
                        backIntent.putExtra("user",user);
                        startActivity(backIntent);
                    }
                });
            }
        });



    }
}
