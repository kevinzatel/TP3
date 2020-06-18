package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MusicianBandSearchActivity extends AppCompatActivity {

    DataBaseHelper db;
    User user;
    String instrument, timeOfDay, district;
    Spinner districtDropDown, timeOfDayDropDown, instrumentDropDown;
    ListView bandsListView;
    ProgressBar progressBarSearch;
    Button searchBandBtn;
    ImageView noResultsImg;
    ArrayList<User> bandsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_band_search);
        db = new DataBaseHelper();
        user = (User) getIntent().getSerializableExtra("user");

        districtDropDown = findViewById(R.id.musicianBandSearchDistrict);
        timeOfDayDropDown = findViewById(R.id.musicianBandSearchTime);
        instrumentDropDown = findViewById(R.id.musicianBandSearchInstrument);
        bandsListView = (ListView) findViewById(R.id.musicianBandSearchList);
        progressBarSearch = (ProgressBar) findViewById(R.id.musicianBandSearchProgressBar);
        noResultsImg = (ImageView) findViewById(R.id.noResultSearch);
        searchBandBtn = (Button) findViewById(R.id.startSearchBtn);

        progressBarSearch.setVisibility(View.INVISIBLE);
        noResultsImg.setVisibility(View.VISIBLE);
        searchBandBtn.setVisibility(View.VISIBLE);

        fillDropDowns();
        activateSearch();

    }

    private void activateSearch(){

        searchBandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchBandProcess searchBandAsyncTask = new SearchBandProcess();
                String [] params = { instrument, timeOfDay, district };
                searchBandAsyncTask.execute(params);
            }
        });
    }

    private class SearchBandProcess extends AsyncTask<String, Void, ArrayList<User>> {

        private String instrumentValue;
        private String timeOfDayValue;
        private String districtValue;

        @Override
        protected void onPreExecute() {
            noResultsImg.setVisibility(View.GONE);
            progressBarSearch.setVisibility(View.VISIBLE);
            searchBandBtn.setVisibility(View.INVISIBLE);
            districtDropDown.setEnabled(false);
            timeOfDayDropDown.setEnabled(false);
            instrumentDropDown.setEnabled(false);
        }

        @Override
        protected ArrayList<User> doInBackground(String... params) {

            this.instrumentValue = params[0];
            this.timeOfDayValue = params[1];
            this.districtValue = params[2];

            ArrayList<User> usersList = db.getBand(instrumentValue, districtValue, timeOfDayValue);
            bandsList = usersList;

            return usersList;
        }

        @Override
        protected void onPostExecute(ArrayList<User> bands) {

            progressBarSearch.setVisibility(View.INVISIBLE);
            searchBandBtn.setVisibility(View.VISIBLE);
            districtDropDown.setEnabled(true);
            timeOfDayDropDown.setEnabled(true);
            instrumentDropDown.setEnabled(true);

            if(bands!=null) {
                BandSearchAdapter bandAdapter = new BandSearchAdapter(user, bands, MusicianBandSearchActivity.this);
                bandsListView.setAdapter(bandAdapter);
                ((BaseAdapter) bandAdapter).notifyDataSetChanged();
            }
            else {
                BandSearchAdapter bandAdapter = new BandSearchAdapter(user, new ArrayList<User>(), MusicianBandSearchActivity.this);
                bandsListView.setAdapter(bandAdapter);
                ((BaseAdapter) bandAdapter).notifyDataSetChanged();
                noResultsImg.setVisibility(View.VISIBLE);
            }
        }
    }

    private void fillDropDowns(){

        String [] districtItems, timeOfDayItems, instrumentItems;
        ArrayAdapter<String> districtAdapter, timeOfDayAdapter, instrumentAdapter;

        districtItems = getResources().getStringArray(R.array.barrios);
        districtAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, districtItems);

        timeOfDayItems = getResources().getStringArray(R.array.turnos);
        timeOfDayAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, timeOfDayItems);

        instrumentItems = getResources().getStringArray(R.array.instrumentos);
        instrumentAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, instrumentItems);

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

        instrumentDropDown.setAdapter(instrumentAdapter);
        instrumentDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
}
