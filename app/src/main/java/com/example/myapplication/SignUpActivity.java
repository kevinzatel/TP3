package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    DataBaseHelper db;
    String type;
    EditText userNameTxt, passwordTxt, confirmPasswordTxt;
    Button signUpBtn;
    int isBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new DataBaseHelper();

        userNameTxt = (EditText) findViewById(R.id.userNameTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        confirmPasswordTxt = (EditText) findViewById(R.id.confirmPasswordTxt);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        fillDropDowns();
        addUser();

    }

    public void addUser(){

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdded;
                String userName = userNameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                String confirmPassword = confirmPasswordTxt.getText().toString();

                if(isUserFormValid(userName, password, confirmPassword)){
                        isAdded = db.insertUser(userName, password, isBand);
                        if(isAdded) {
                            Intent firstTimeIntent;
                            if(isBand()){
                                firstTimeIntent = new Intent(getApplicationContext(), BandFirstTimeActivity.class);
                            }
                            else {
                                firstTimeIntent = new Intent(getApplicationContext(), MusicianFirstTimeActivity.class);
                            }
                            firstTimeIntent.putExtra("userName", userName);
                            startActivity(firstTimeIntent);
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Ocurrió un error interno. Por favor, intentá nuevamente", Toast.LENGTH_LONG).show();
                        }
                }
            }
        });
    }

    private boolean isBand(){
        return type.equals(getResources().getStringArray(R.array.tipo)[1]);
    }

    private void fillDropDowns(){

        final Spinner tipoDropDown;
        String [] tipoItems;
        ArrayAdapter<String> tipoAdapter;

        tipoDropDown = findViewById(R.id.tipoDropDown);
        tipoItems = getResources().getStringArray(R.array.tipo);
        tipoAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, tipoItems);
        tipoDropDown.setAdapter(tipoAdapter);
        tipoDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parent, View view,
                                                                              int position, long id) {
                                                       type = (String) parent.getItemAtPosition(position);
                                                       if(isBand()){
                                                           isBand = 1;
                                                       }
                                                       else{
                                                           isBand = 0;
                                                       }
                                                   }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private boolean isUserFormValid(String userName, String password, String confirmPassword){

        Boolean isValid = true;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(userName.isEmpty()){
            userNameTxt.setError("Por favor, ingresá tu email");
            isValid = false;
        } else if (!(userName.trim().matches(emailPattern))){
            userNameTxt.setError("Por favor, ingresá una email valido");
            isValid = false;
        } else if(db.getUser(userName) != null){
            userNameTxt.setError("Este usuario ya esta registrado. Ingresá otro");
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
        if(confirmPassword.isEmpty()){
            confirmPasswordTxt.setError("Confirmá tu contraseña");
            isValid = false;
        } else if (!password.equals(confirmPassword)){
            confirmPasswordTxt.setError("Las contreseñas tienen que ser iguales");
            isValid = false;
        } else {
            confirmPasswordTxt.setError(null);
        }
        return isValid;
    }

}
