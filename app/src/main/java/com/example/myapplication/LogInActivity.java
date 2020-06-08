package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    private DataBaseHelper db;
    private Button signInBtn;
    private TextView signUpLink;
    private EditText userNameTxt;
    private EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseHelper();

        signInBtn = (Button) findViewById(R.id.signInBtn);
        signUpLink = (TextView) findViewById(R.id.signUpLink);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userNameTxt = (EditText) findViewById(R.id.userNameTxt);
                passwordTxt = (EditText) findViewById(R.id.passwordTxt);

                String userName = userNameTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                if (isUserFormValid(userName, password)){

                    User user = db.getUser(userName);

                    if(user == null){
                        Toast.makeText(LogInActivity.this, "La cuenta que ingresaste no existe. Creala", Toast.LENGTH_LONG).show();
                        Intent signUpintent;
                        signUpintent = new Intent(getApplicationContext(), SignUpActivity.class);
                        startActivity(signUpintent);
                    }
                    else if(!user.isActive()){
                        Toast.makeText(LogInActivity.this, "Todavia no activaste tu cuenta. Activala", Toast.LENGTH_LONG).show();
                        Intent activateIntent;
                        if(user.isBand()){
                            activateIntent = new Intent(getApplicationContext(), BandFirstTimeActivity.class);
                        }
                        else {
                            activateIntent = new Intent(getApplicationContext(), MusicianFirstTimeActivity.class);
                        }
                        activateIntent.putExtra("userName", userName);
                        startActivity(activateIntent);
                    } else if(userName.equals(user.getUserName()) && password.equals(user.getPassword())){
                        Intent landingIntent;
                        if(user.isBand()){
                            landingIntent = new Intent(getApplicationContext(), BandLandingPageActivity.class);
                            landingIntent.putExtra("user", user);
                        }
                        else{
                            landingIntent = new Intent(getApplicationContext(), MusicianLandingActivity.class);
                            landingIntent.putExtra("userName", userName);
                        }
                        startActivity(landingIntent);
                    }
                    else{
                        Toast.makeText(LogInActivity.this, "Los datos que ingresaste son incorrectos", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    private boolean isUserFormValid(String userName, String password){

        Boolean isValid = true;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(userName.isEmpty()){
            userNameTxt.setError("Por favor, ingresá tu email");
            isValid = false;
        } else if (!(userName.trim().matches(emailPattern))){
            userNameTxt.setError("Por favor, ingresá una email valido");
            isValid = false;
        } else {
            userNameTxt.setError(null);
        }
        if(password.isEmpty()){
            passwordTxt.setError("Por favor, ingresá una contraseña valida");
            isValid = false;
        } else {
            passwordTxt.setError(null);
        }

        return isValid;
    }

}
