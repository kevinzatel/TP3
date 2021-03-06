package com.example.myapplication;

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
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Locale;

public class BandFirstTimeActivity extends AppCompatActivity {

    DataBaseHelper db;
    String userName, nickname, phone, timeOfDay, district;
    Address address;
    ImageView checkOkIcon;
    EditText nicknameTxt, phoneTxt, addressTxt;
    ProgressBar progressBarBand;
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
        checkOkIcon = (ImageView) findViewById(R.id.imageIconCheckBandFirst);
        searchAdressBtn = (Button) findViewById(R.id.searchAdressBandBtn);
        progressBarBand = (ProgressBar) findViewById(R.id.progressBarBandFirst);
        createBtn = (Button) findViewById(R.id.createBandBtn);

        progressBarBand.setVisibility(View.INVISIBLE);
        checkOkIcon.setVisibility(View.INVISIBLE);

        fillDropDowns();
        activateUser();

    }

    private void activateUser(){

        searchAdressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOkIcon.setVisibility(View.INVISIBLE);
                String getAddress = addressTxt.getText().toString();
                if (isValidAddress(getAddress)){
                    addressTxt.setError(null);
                    SearchLocationProcess searchAddressAsyncTask = new SearchLocationProcess();
                    String [] params = { getAddress };
                    searchAddressAsyncTask.execute(params);
                } else {
                    addressTxt.setError("Por favor, ingresá una dirección válida");
                }
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = nicknameTxt.getText().toString();
                phone = phoneTxt.getText().toString();
                String streetAndNumber = addressTxt.getText().toString();
                if(isUserFormValid(nickname, phone, address)){
                    ActivateBandProcess activateBandnAsyncTask = new ActivateBandProcess();
                    String [] params = { userName, nickname, phone, timeOfDay, district, streetAndNumber, String.valueOf(address.getLatitude()), String.valueOf(address.getLongitude()) };
                    activateBandnAsyncTask.execute(params);
                }
            }
        });
    }

    public class ActivateBandProcess extends AsyncTask<String, Void, Boolean> {

        private String userName;
        private String nickname;
        private String phone;
        private String timeOfDay;
        private String district;
        private String address;
        private String latitude;
        private String longitude;

        @Override
        protected void onPreExecute() {
            progressBarBand.setVisibility(View.VISIBLE);
            createBtn.setEnabled(false);
            searchAdressBtn.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {

            this.userName = params[0];
            this.nickname = params[1];
            this.phone = params[2];
            this.timeOfDay = params[3];
            this.district = params[4];
            this.address = params[5];
            this.latitude = params[6];
            this.longitude = params[7];

            boolean isCreated = db.activateBand(this.userName, this.nickname, this.phone, this.timeOfDay, this.district, this.address, this.latitude, this.longitude);

            return isCreated;
        }

        @Override
        protected void onPostExecute(Boolean isCreated) {

            progressBarBand.setVisibility(View.INVISIBLE);
            createBtn.setEnabled(true);
            searchAdressBtn.setEnabled(true);

            if(isCreated) {
                Intent loginIntent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(loginIntent);
            }
            else {
                Toast.makeText(BandFirstTimeActivity.this, "Ocurrió un error interno. Por favor, intentá nuevamente", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class SearchLocationProcess extends AsyncTask<String, Void, Address> {

        private String getAddress;

        @Override
        protected void onPreExecute() {

            checkOkIcon.setVisibility(View.INVISIBLE);
            progressBarBand.setVisibility(View.VISIBLE);
            createBtn.setEnabled(false);
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
            createBtn.setEnabled(true);
            searchAdressBtn.setEnabled(true);

            if (address == null) {
                addressTxt.setError("No se encontró ninguna ubicación con los datos que ingresaste");
            } else {
                addressTxt.setError(null);
                checkOkIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    private Address getLocation(String location){

        Address address = null;
        Geocoder geoCoder = new Geocoder(BandFirstTimeActivity.this, Locale.getDefault());

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

    private void fillDropDowns(){

        final Spinner districtDropDown, timeOfDayDropDown;
        String [] districtItems, timeOfDayItems;
        ArrayAdapter<String> districtAdapter, timeOfDayAdapter;

        districtDropDown = findViewById(R.id.districtBandDropDown);
        districtItems = getResources().getStringArray(R.array.barrios);
        districtAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, districtItems);

        timeOfDayDropDown = findViewById(R.id.timeOfDayBandDropDown);
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

    private boolean isUserFormValid(String nickname, String phone, Address address){

        Boolean isValid = true;

        if(nickname.isEmpty()){
            nicknameTxt.setError("Por favor, ingresá el nombre de tu banda");
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
        if(address == null){
            addressTxt.setError("Por favor, ingresá una dirección valida");
            isValid = false;
        } else {
            addressTxt.setError(null);
        }
        return isValid;
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
