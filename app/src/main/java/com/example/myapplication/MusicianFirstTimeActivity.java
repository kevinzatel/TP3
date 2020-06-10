package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MusicianFirstTimeActivity extends AppCompatActivity {

    DataBaseHelper db;
    String userName, nickname, phone;
    EditText nicknameTxt, phoneTxt;
    ProgressBar progressBarMusician;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_first_time);
        db = new DataBaseHelper();
        userName = (String) getIntent().getSerializableExtra("userName");

        nicknameTxt = (EditText) findViewById(R.id.nicknameMusicianTxt);
        phoneTxt = (EditText) findViewById(R.id.phoneMusicianTxt);
        progressBarMusician = (ProgressBar) findViewById(R.id.progressBarMusicianFirst);
        createBtn = (Button) findViewById(R.id.createMusicianBtn);

        progressBarMusician.setVisibility(View.GONE);

        activateUser();

    }

    private void activateUser(){

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = nicknameTxt.getText().toString();
                phone = phoneTxt.getText().toString();

                if(isUserFormValid(nickname, phone)){
                    ActivateMusicianProcess activateMusicianAsyncTask = new ActivateMusicianProcess();
                    String [] params = { userName, nickname, phone };
                    activateMusicianAsyncTask.execute(params);
                }
            }
        });
    }

    private class ActivateMusicianProcess extends AsyncTask<String, Void, Boolean> {

        private String userName;
        private String nickname;
        private String phone;

        @Override
        protected void onPreExecute() {
            progressBarMusician.setVisibility(View.VISIBLE);
            createBtn.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {

            this.userName = params[0];
            this.nickname = params[1];
            this.phone = params[2];

            boolean isCreated = db.activateMusician(userName, nickname, phone);

            return isCreated;
        }

        @Override
        protected void onPostExecute(Boolean isCreated) {

            progressBarMusician.setVisibility(View.GONE);
            createBtn.setEnabled(true);

            if(isCreated) {
                Intent loginIntent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(loginIntent);
            }
            else {
                Toast.makeText(MusicianFirstTimeActivity.this, "Ocurrió un error interno. Por favor, intentá nuevamente", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isUserFormValid(String nickname, String phone){

        Boolean isValid = true;

        if(nickname.isEmpty()){
            nicknameTxt.setError("Por favor, ingresá tu nombre");
            isValid = false;
        } else {
            nicknameTxt.setError(null);
        }
        if(phone.isEmpty()){
            phoneTxt.setError("Por favor, ingresá una número de contacto");
            isValid = false;
        } else if(!isNumeric(phone)){
            phoneTxt.setError("Por favor, ingresá una número valido");
            isValid = false;
        } else if(!(phone.length() == 10)){
            phoneTxt.setError("Por favor, ingresá una número de 10 dígitos");
            isValid = false;
        } else {
            phoneTxt.setError(null);
        }
        return isValid;
    }

    private boolean isNumeric(String strNum) {

        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
