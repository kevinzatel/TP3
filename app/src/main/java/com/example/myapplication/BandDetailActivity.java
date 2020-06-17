package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BandDetailActivity extends AppCompatActivity {

    private DataBaseHelper db;
    private User userLogged;
    private User userBand;
    private Button requestBtn;
    private TextView bandNameTxt;
    private TextView timeOfDayTxt;
    private TextView districtTxt;
    private ProgressBar progressBar;
    private ArrayList<User> externalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_detail);
        db = new DataBaseHelper();
        externalData = new ArrayList<>();

        externalData = (ArrayList<User>) getIntent().getSerializableExtra("data");
        userLogged = (User) externalData.get(0);
        userBand = (User) externalData.get(1);
        bandNameTxt = (TextView) findViewById(R.id.bandDetailNameTxt);
        timeOfDayTxt = (TextView) findViewById(R.id.bandDetailDayTime);
        districtTxt = (TextView) findViewById(R.id.bandDetailDistrictTxt);
        requestBtn = (Button) findViewById(R.id.sendRequestBtn);
        progressBar = (ProgressBar) findViewById(R.id.bandDetailProgressBar);

        bandNameTxt.setText(userBand.getNickname());
        timeOfDayTxt.setText(userBand.getTimeOfDay());
        districtTxt.setText(userBand.getDistrict());

        progressBar.setVisibility(View.INVISIBLE);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BandDetailActivity.this, "Solicitud enviada a " + userBand.getUserName(), Toast.LENGTH_LONG).show();
            }
        });

    }

//    private class MakeRequest extends AsyncTask<String, Void, User> {
//
//        private String userName;
//        private String password;
//
//        @Override
//        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
//            signInBtn.setEnabled(false);
//            signUpLink.setEnabled(false);
//        }
//
//        @Override
//        protected User doInBackground(String... params) {
//
//            this.userName = params[0];
//            this.password = params[1];
//
//            User user = db.getUser(this.userName);
//
//            return user;
//        }
//
//        @Override
//        protected void onPostExecute(User user) {
//
//            progressBar.setVisibility(View.INVISIBLE);
//            signInBtn.setEnabled(true);
//            signUpLink.setEnabled(true);
//
//            if(user == null){
//                Toast.makeText(LogInActivity.this, "La cuenta que ingresaste no existe. Creala", Toast.LENGTH_LONG).show();
//                Intent signUpintent;
//                signUpintent = new Intent(getApplicationContext(), SignUpActivity.class);
//                startActivity(signUpintent);
//            }
//            else if(!user.isActive()){
//                Toast.makeText(LogInActivity.this, "Todavia no activaste tu cuenta. Activala", Toast.LENGTH_LONG).show();
//                Intent activateIntent;
//                if(user.isBand()){
//                    activateIntent = new Intent(getApplicationContext(), BandFirstTimeActivity.class);
//                }
//                else {
//                    activateIntent = new Intent(getApplicationContext(), MusicianFirstTimeActivity.class);
//                }
//                activateIntent.putExtra("userName", this.userName);
//                startActivity(activateIntent);
//            } else if(this.userName.equals(user.getUserName()) && this.password.equals(user.getPassword())){
//                Intent landingIntent;
//                if(user.isBand()){
//                    landingIntent = new Intent(getApplicationContext(), BandLandingPageActivity.class);
//                    landingIntent.putExtra("user", user);
//                }
//                else{
//                    landingIntent = new Intent(getApplicationContext(), MusicianLandingActivity.class);
//                    landingIntent.putExtra("user", user);
//                }
//                startActivity(landingIntent);
//            }
//            else {
//                Toast.makeText(LogInActivity.this, "Los datos que ingresaste son incorrectos", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
