package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BandFirstTimeActivity extends AppCompatActivity {

    DataBaseHelper db;
    String userName, nickname, phone, timeOfDay, district;
    Address address;
    EditText nicknameTxt, phoneTxt, addressTxt;
    Button createBtn, searchAdressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_first_time);
        db = new DataBaseHelper();
        userName = (String) getIntent().getSerializableExtra("userName");

        nicknameTxt = (EditText) findViewById(R.id.nicknameBandTxt);
        phoneTxt = (EditText) findViewById(R.id.phoneBandTxt);
        addressTxt = (EditText) findViewById(R.id.addressBandTxt);
        searchAdressBtn = (Button) findViewById(R.id.searchAdressBandBtn);
        createBtn = (Button) findViewById(R.id.createBandBtn);

        fillDropDowns();
        activateUser();

    }

    private void activateUser(){

        searchAdressBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                String getAddress = addressTxt.getText().toString();
                if (!getAddress.isEmpty()){
                    address = getLocation(getAddress);
                    if (address == null) {
                        addressTxt.setError("No se encontró ninguna ubicación con los datos que ingresaste");
                    } else {
                        ShapeDrawable shape = new ShapeDrawable(new RectShape());
                        shape.getPaint().setColor(Color.GREEN);
                        shape.getPaint().setStyle(Paint.Style.STROKE);
                        shape.getPaint().setStrokeWidth(3);
                        addressTxt.setBackground(shape);
                    }
                } else {
                    addressTxt.setError("Por favor, ingresá una dirección");
                }
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCreated;
                nickname = nicknameTxt.getText().toString();
                phone = phoneTxt.getText().toString();
                if(isUserFormValid(nickname, phone, address)){
                    isCreated = db.activateBand(userName, nickname, phone, timeOfDay, district, String.valueOf(address.getLatitude()) + "|" + String.valueOf(address.getLongitude()));
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

    private Address getLocation(String location){

        Address address = null;
        Geocoder geoCoder = new Geocoder(BandFirstTimeActivity.this, Locale.getDefault());

        try {
            List<Address> addresses = geoCoder.getFromLocationName(location + "," + getResources().getStringArray(R.array.ciudad_busqueda)[0] + "," + getResources().getStringArray(R.array.provincia_busqueda)[0] + "," + getResources().getStringArray(R.array.pais_busqueda)[0], 1);
            if (addresses.size() > 0) {

                Boolean found = false;
                String localidad = addresses.get(0).getSubLocality().toUpperCase();
                String [] localidades = getResources().getStringArray(R.array.barrios);

                for (String l : localidades) {
                    if(l.toUpperCase().equals(localidad)){
                        found = true;
                        break;
                    }
                }

                if (found) { address = addresses.get(0); }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
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

    private boolean isUserFormValid(String nickname, String phone, Address address){

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
        if(address == null){
            addressTxt.setError("Por favor, ingresá una dirección valida");
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
