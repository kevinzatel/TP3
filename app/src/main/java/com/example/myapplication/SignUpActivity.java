package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    DataBaseHelper db;
    EditText userNameTxt, passwordTxt, confirmPasswordTxt;
    Button signUpBtn;
    String type, instrument;
    int isBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new DataBaseHelper(this);

        userNameTxt = (EditText) findViewById(R.id.userNameTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        confirmPasswordTxt = (EditText) findViewById(R.id.confirmPasswordTxt);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        fillDropDowns();
        AddUser();
    }

    public void AddUser(){
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdded;
                String userName = userNameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                String confirmPassword = confirmPasswordTxt.getText().toString();
                if(isUserFormValid(userName, password, confirmPassword)){
                        isAdded = db.insertUser(userName, password, instrument, isBand);
                        if(isAdded) {
                            Toast.makeText(SignUpActivity.this, "Created Successfully", Toast.LENGTH_LONG).show();
                            ShowUsers();
                        }
                        else
                            Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isBand(){
        return type.equals(getResources().getStringArray(R.array.tipo)[1]);
    }

    private void fillDropDowns(){
        final Spinner tipoDropDown, instrumentoDropDown;
        String[] tipoItems, instrumentoItems;
        ArrayAdapter<String> tipoAdapter, instrumentoAdapter;
        final TextView instrumentoTxt;

        tipoDropDown = findViewById(R.id.tipoDropDown);
        tipoItems = getResources().getStringArray(R.array.tipo);
        tipoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipoItems);

        instrumentoTxt = findViewById(R.id.instrumentoTxt);
        instrumentoDropDown = findViewById(R.id.instrumentoDropDown);
        instrumentoItems = getResources().getStringArray(R.array.instrumentos);
        instrumentoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, instrumentoItems);

        tipoDropDown.setAdapter(tipoAdapter);
        tipoDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parent, View view,
                                                                              int position, long id) {
                                                       type = (String) parent.getItemAtPosition(position);
                                                       if(isBand()){
                                                           instrumentoDropDown.setVisibility(View.GONE);
                                                           instrumentoTxt.setVisibility(View.GONE);
                                                           instrument = null;
                                                           isBand = 1;
                                                       }
                                                       else{
                                                           instrumentoDropDown.setVisibility(View.VISIBLE);
                                                           instrumentoTxt.setVisibility(View.VISIBLE);
                                                           isBand = 0;
                                                       }
                                                   }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        instrumentoDropDown.setAdapter(instrumentoAdapter);
        instrumentoDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                instrument = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isUserFormValid(String userName, String password, String confirmPassword){
        Boolean isValid = true;

        if(userName.isEmpty()){
            userNameTxt.setError("Ingrese un usuario por favor.");
            isValid = false;
        } else if(db.getUser(userName) != null){
            userNameTxt.setError("Este nombre de usuario ya existe. Ingrese otro por favor.");
            isValid = false;
        }
        if(password.isEmpty()){
            passwordTxt.setError("Ingrese una contreseña por favor.");
            isValid = false;
        }
        if(confirmPassword.isEmpty()){
            confirmPasswordTxt.setError("Confirme la contreseña por favor.");
            isValid = false;
        }
        if(!password.equals(confirmPassword)){
            confirmPasswordTxt.setError("Las contreseñas deben coincidir.");
            isValid = false;
        }
        return isValid;
    }

    public void ShowUsers(){
        String users = db.getUsers();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(users);
        builder.show();
    }
}
