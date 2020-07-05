package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    EditText nickTxt, telefonoTxt, direccionTxt;
    Spinner timeOfDayDropDown, districtDropDown;
    DataBaseHelper db;
    Button guardarBtn, volverBtn, searchAdressBtn;
    String nickname, phone, timeOfDay, district, direccion, latitude, longitude;
    Address address;
    ImageView checkOkIcon;
    ArrayAdapter<String> districtAdapter, timeOfDayAdapter;
    ProgressBar progressBarBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = new DataBaseHelper();
        final User user = (User) getIntent().getSerializableExtra("user");
        guardarBtn = findViewById(R.id.guardarPerfilBtn);
        volverBtn = findViewById(R.id.volverPerfilBtn);
        nickTxt = findViewById(R.id.editNickTxt);
        telefonoTxt = findViewById(R.id.editTelefonoTxt);
        direccionTxt = findViewById(R.id.editDireccionTxt);
        timeOfDayDropDown = findViewById(R.id.editDisponibilidadDrop);
        districtDropDown = findViewById(R.id.editLocalidadDrop);
        checkOkIcon = findViewById(R.id.checkOkImg);
        searchAdressBtn = findViewById(R.id.validarDireccionBtn);
        progressBarBand = findViewById(R.id.editProfileProgressBar);

        fillDropDowns();
        fillInitialValues(user);

        volverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver(user.getUserName());
            }
        });

        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = nickTxt.getText().toString();
                phone = telefonoTxt.getText().toString();
                direccion = direccionTxt.getText().toString();

                if(validateAdress() && isUserFormValid(nickname, phone, address)){
                    latitude = String.valueOf(address.getLatitude());
                    longitude = String.valueOf(address.getLongitude());
                try{
                    db.updateBandProfile(user, nickname, phone, direccion, latitude, longitude, district, timeOfDay);
                    Toast.makeText(EditProfileActivity.this, "Datos Actualizados", Toast.LENGTH_LONG).show();
                    volver(user.getUserName());
                }catch (Exception ex){
                    Toast.makeText(EditProfileActivity.this, "Ocurrió un error interno. Por favor, intentá nuevamente", Toast.LENGTH_LONG).show();
                }
                }
            }
        });

        searchAdressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOkIcon.setVisibility(View.INVISIBLE);
                validateAdress();
            }
        });

    }

    private boolean validateAdress(){
        boolean isValid;
        String getAddress = direccionTxt.getText().toString();
        if (isValidAddress(getAddress)){
            direccionTxt.setError(null);
            SearchLocationProcessClass searchAddressAsyncTask = new SearchLocationProcessClass();
            String [] params = { getAddress };
            searchAddressAsyncTask.execute(params);
            isValid = true;
        } else {
            direccionTxt.setError("Por favor, ingresá una dirección válida");
            isValid = false;
        }
        return isValid;
    }

    private boolean isUserFormValid(String nickname, String phone, Address address){

        Boolean isValid = true;

        if(nickname.isEmpty()){
            nickTxt.setError("Por favor, ingresá el nombre de tu banda");
            isValid = false;
        } else {
            nickTxt.setError(null);
        }
        if(phone.isEmpty()){
            telefonoTxt.setError("Por favor, ingresá una número de contacto");
            isValid = false;
        } else if(!isNumeric(phone)){
            telefonoTxt.setError("Por favor, ingresá una número valido");
            isValid = false;
        } else if(!(phone.length() == 10)){
            telefonoTxt.setError("Por favor, ingresá una número de 10 dígitos");
            isValid = false;
        } else {
            telefonoTxt.setError(null);
        }
        if(address == null){
            direccionTxt.setError("Por favor, ingresá una dirección valida");
            isValid = false;
        } else {
            direccionTxt.setError(null);
        }
        return isValid;
    }

    private void volver(String userName) {
        User updatedUser = db.getUser(userName);
        Intent landingIntent = new Intent(getApplicationContext(), BandLandingPageActivity.class);
        landingIntent.putExtra("user", updatedUser);
        startActivity(landingIntent);
    }

    private void fillInitialValues(User user) {
        nickTxt.setText(user.getNickname());
        telefonoTxt.setText(user.getPhone());
        direccionTxt.setText(user.getAddress());
        int districtPosition = districtAdapter.getPosition(user.getDistrict());
        districtDropDown.setSelection(districtPosition);
        int timeOfDayPosition = timeOfDayAdapter.getPosition(user.getTimeOfDay());
        timeOfDayDropDown.setSelection(timeOfDayPosition);
    }

    private void fillDropDowns(){
        String [] districtItems, timeOfDayItems;

        districtItems = getResources().getStringArray(R.array.barrios);
        districtAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, districtItems);

        timeOfDayItems = getResources().getStringArray(R.array.turnos);
        timeOfDayAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, timeOfDayItems);

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


    private class SearchLocationProcessClass extends AsyncTask<String, Void, Address> {

        private String getAddress;

        @Override
        protected void onPreExecute() {
            progressBarBand.setVisibility(View.VISIBLE);
            checkOkIcon.setVisibility(View.INVISIBLE);
            guardarBtn.setEnabled(false);
            searchAdressBtn.setEnabled(false);
        }

        @Override
        protected Address doInBackground(String... params) {

            this.getAddress = params[0];
            address = getLocation(this.getAddress);
            return address;
        }

        @Override
        protected void onPostExecute(Address responseAddress) {
            progressBarBand.setVisibility(View.INVISIBLE);
            guardarBtn.setEnabled(true);
            searchAdressBtn.setEnabled(true);

            if (address == null) {
                direccionTxt.setError("No se encontró ninguna ubicación con los datos que ingresaste");
            } else {
                direccionTxt.setError(null);
                checkOkIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    private Address getLocation(String location){

        Address address = null;
        Geocoder geoCoder = new Geocoder(EditProfileActivity.this, Locale.getDefault());

        try {
            List<Address> addresses = geoCoder.getFromLocationName(location + "," + getResources().getStringArray(R.array.ciudad_busqueda)[0] + "," + getResources().getStringArray(R.array.provincia_busqueda)[0] + "," + getResources().getStringArray(R.array.pais_busqueda)[0], 1);
            if (addresses.size() > 0) {

                Boolean found = false;
                String localidad = (addresses.get(0).getSubLocality() != null) ? addresses.get(0).getSubLocality().toUpperCase() : null;

                if(localidad != null) {
                    if (district.toUpperCase().equals(localidad)){
                        found = true;
                    }
                }
                if (found) { address = addresses.get(0); }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return address;
    }

    private boolean isValidAddress(String address){
        boolean isValid = true;

        if(address == null){
            isValid = false;
        } else if(address.isEmpty()){
            isValid = false;
        } else if((address.length() < 3) || (address.length() > 50)){
            isValid = false;
        } else if((address.split(" ").length < 2) || (address.split(" ").length > 10)){
            isValid = false;
        } else if(!isNumeric((address.split(" ")[(address.split(" ").length) - 1]))){
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
