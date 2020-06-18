package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditAccountActivity extends AppCompatActivity {
    EditText emailTxt, passwordTxt, confirmPasswordTxt;
    Button volverBtn, guardarBtn;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        db = new DataBaseHelper();
        final User user = (User) getIntent().getSerializableExtra("user");
        guardarBtn = findViewById(R.id.guardarPerfilBtn);
        volverBtn = findViewById(R.id.volverPerfilBtn);
        emailTxt = findViewById(R.id.editEmailTxt);
        passwordTxt = findViewById(R.id.editPasswordTxt);
        confirmPasswordTxt = findViewById(R.id.editConfirmPasswordTxt);
        fillInitialValues(user);

        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = emailTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                String confirmPassword = confirmPasswordTxt.getText().toString();

                if(isUserFormValid(user, userName, password, confirmPassword)){
                    try{
                        db.updateBandAccount(user, userName, password);
                        Toast.makeText(EditAccountActivity.this, "Datos actualizados correctamente", Toast.LENGTH_LONG).show();
                        volver(userName);
                    }catch(Exception ex)
                    {
                        Toast.makeText(EditAccountActivity.this, "Error en la conexión con la base de datos.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        volverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver(user.getUserName());
            }
        });
    }

    private void volver(String userName) {
        User updatedUser = db.getUser(userName);
        Intent landingIntent = new Intent(getApplicationContext(), BandLandingPageActivity.class);
        landingIntent.putExtra("user", updatedUser);
        startActivity(landingIntent);
    }

    private void fillInitialValues(User user) {
        emailTxt.setText(user.getUserName());
    }

    private boolean isUserFormValid(User user, String userName, String password, String confirmPassword){

        Boolean isValid = true;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(userName.isEmpty()){
            emailTxt.setError("Por favor, ingresá tu email");
            isValid = false;
        } else if (!(userName.trim().matches(emailPattern))){
            emailTxt.setError("Por favor, ingresá una email valido");
            isValid = false;
        }
        else if(!user.getUserName().equals(userName) && db.getUser(userName) != null){
            emailTxt.setError("Este usuario ya esta registrado. Ingresá otro");
           isValid = false;
        }
        else {
            emailTxt.setError(null);
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
