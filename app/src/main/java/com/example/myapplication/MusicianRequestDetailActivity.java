package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class MusicianRequestDetailActivity extends AppCompatActivity {

    private DataBaseHelper db;
    private MapView googleMapView;
    private GoogleMap googleMap;
    private TextView address;
    private TextView email;
    private TextView phone;
    private ProgressBar progressBar;
    private Requests request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.musician_request_detail_activity);
        db = new DataBaseHelper();
        googleMapView = (MapView) findViewById(R.id.mapViewDetail);
        address = (TextView) findViewById(R.id.emailRequestDetailTxt);
        email = (TextView) findViewById(R.id.addressRequestDetailTxt);
        phone = (TextView) findViewById(R.id.phoneRequestDetail);
        progressBar = (ProgressBar) findViewById(R.id.progressBarDetail);
        request = (Requests) getIntent().getSerializableExtra("request");

        FillData fillDataAsyncTask = new FillData();
        fillDataAsyncTask.execute();

    }

    private class FillData extends AsyncTask<Void, Void, User> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            googleMapView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected User doInBackground(Void... voids) {

            User user = db.getUser(request.getIdBand());
            return user;

        }

        @Override
        protected void onPostExecute(User user) {

            if(user == null){
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MusicianRequestDetailActivity.this, "Ocurrió un error interno. Intentá nuevamente", Toast.LENGTH_LONG).show();
            }
            else {
                address.setText(user.getAddress() + ", " + user.getDistrict());
                email.setText(user.getUserName());
                phone.setText(user.getPhone());
                //SET POSITION ON MAP
                progressBar.setVisibility(View.INVISIBLE);
                googleMapView.setVisibility(View.VISIBLE);
            }
        }
    }
}
