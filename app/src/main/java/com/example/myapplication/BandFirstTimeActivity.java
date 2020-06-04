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

public class BandFirstTimeActivity extends AppCompatActivity {

    DataBaseHelper db;
    String userName, nickname, phone, timeOfDay, district;
    EditText nicknameTxt, phoneTxt;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_first_time);
        db = new DataBaseHelper();
        userName = (String) getIntent().getSerializableExtra("userName");

        nicknameTxt = (EditText) findViewById(R.id.nicknameBandTxt);
        phoneTxt = (EditText) findViewById(R.id.phoneBandTxt);
        createBtn = (Button) findViewById(R.id.createBandBtn);

        fillDropDowns();
        activateUser();

    }

    private void activateUser(){

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCreated;
                nickname = nicknameTxt.getText().toString();
                phone = phoneTxt.getText().toString();

                if(isUserFormValid(nickname, phone)){
                    isCreated = db.activateBand(userName, nickname, phone, timeOfDay, district, null);
                    if(isCreated) {
                        Intent loginIntent = new Intent(getApplicationContext(), LogInActivity.class);
                        startActivity(loginIntent);
                    }
                    else {
                        Toast.makeText(BandFirstTimeActivity.this, "Ocurrió un error interno. Por favor, intentá nuevamente", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void fillDropDowns(){

        final Spinner districtDropDown, timeOfDayDropDown;
        String [] districtItems, timeOfDayItems;
        ArrayAdapter<String> districtAdapter, timeOfDayAdapter;

        districtDropDown = findViewById(R.id.districtBandDropDown);
        districtItems = getResources().getStringArray(R.array.barrios);
        districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districtItems);

        timeOfDayDropDown = findViewById(R.id.timeOfDayBandDropDown);
        timeOfDayItems = getResources().getStringArray(R.array.turnos);
        timeOfDayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeOfDayItems);

        districtDropDown.setAdapter(districtAdapter);
        districtDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                district = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timeOfDayDropDown.setAdapter(timeOfDayAdapter);
        timeOfDayDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                timeOfDay = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isUserFormValid(String nickname, String phone){

        Boolean isValid = true;

        if(nickname.isEmpty()){
            nicknameTxt.setError("Por favor, ingresá el nombre de tu banda");
            isValid = false;
        }
        if(phone.isEmpty()){
            phoneTxt.setError("Por favor, ingresá una número de contacto");
            isValid = false;
        }
        if(!isNumeric(phone)){
            phoneTxt.setError("Por favor, ingresá una número valido");
            isValid = false;
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
