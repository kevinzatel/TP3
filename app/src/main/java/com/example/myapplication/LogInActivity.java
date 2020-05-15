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
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseHelper(this);

        Button signInBtn = (Button) findViewById(R.id.signInBtn);
        TextView signUpLink = (TextView) findViewById(R.id.signUpLink);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userNameTxt = (EditText) findViewById(R.id.userNameTxt);
                EditText passwordTxt = (EditText) findViewById(R.id.passwordTxt);

                String userName = userNameTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                User user = db.getUser(userName);

                if(user != null && userName.equals(user.getUserName()) && password.equals(user.getPassword())){
                    Intent landingIntent;
                    if(user.getIsBand()){
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
                    Toast.makeText(LogInActivity.this, "Log In Denied", Toast.LENGTH_LONG).show();
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
}
