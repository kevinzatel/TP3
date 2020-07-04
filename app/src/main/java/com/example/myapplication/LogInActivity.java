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

public class LogInActivity extends AppCompatActivity {

    private Session session;
    private String sessionUsername;
    private DataBaseHelper db;
    private Button signInBtn;
    private TextView signUpLink;
    private EditText userNameTxt;
    private EditText passwordTxt;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        db = new DataBaseHelper();
        session = new Session(getApplicationContext());
        sessionUsername = session.getSessionUsername();

        if (sessionUsername != null && !sessionUsername.equals("")){

            Intent landingIntent;
            User userSession = db.getUser(sessionUsername.trim());

            if(userSession.isBand()){
                landingIntent = new Intent(getApplicationContext(), BandLandingPageActivity.class);
                landingIntent.putExtra("user", userSession);
            }
            else{
                landingIntent = new Intent(getApplicationContext(), MusicianLandingActivity.class);
                landingIntent.putExtra("user", userSession);
            }
            startActivity(landingIntent);
        }

        setContentView(R.layout.activity_main);

        signInBtn = (Button) findViewById(R.id.signInBtn);
        signUpLink = (TextView) findViewById(R.id.signUpLink);
        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);

        progressBar.setVisibility(View.INVISIBLE);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userNameTxt = (EditText) findViewById(R.id.userNameTxt);
                passwordTxt = (EditText) findViewById(R.id.passwordTxt);

                String userName = userNameTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                if (isUserFormValid(userName, password)){

                    LoginProcess signInAsyncTask = new LoginProcess();
                    String [] params = { userName, password};
                    signInAsyncTask.execute(params);
                    
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

    private class LoginProcess extends AsyncTask<String, Void, User> {

        private String userName;
        private String password;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            signInBtn.setEnabled(false);
            signUpLink.setEnabled(false);
        }

        @Override
        protected User doInBackground(String... params) {

            this.userName = params[0];
            this.password = params[1];

            User user = db.getUser(this.userName);

            return user;
        }

        @Override
        protected void onPostExecute(User user) {

            progressBar.setVisibility(View.INVISIBLE);
            signInBtn.setEnabled(true);
            signUpLink.setEnabled(true);

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
                activateIntent.putExtra("userName", this.userName);
                startActivity(activateIntent);
            } else if(this.userName.equals(user.getUserName()) && this.password.equals(user.getPassword())){
                Intent landingIntent;
                session.setSessionUsername(userName.trim());
                if(user.isBand()){
                    landingIntent = new Intent(getApplicationContext(), BandLandingPageActivity.class);
                    landingIntent.putExtra("user", user);
                }
                else{
                    landingIntent = new Intent(getApplicationContext(), MusicianLandingActivity.class);
                    landingIntent.putExtra("user", user);
                }
                startActivity(landingIntent);
            }
            else {
                Toast.makeText(LogInActivity.this, "Los datos que ingresaste son incorrectos", Toast.LENGTH_LONG).show();
            }
        }
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
