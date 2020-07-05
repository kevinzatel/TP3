package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MusicianRequestDetailActivity extends AppCompatActivity {

    private DataBaseHelper db;
    private User user;
    private MapView googleMapView;
    private TextView address;
    private TextView email;
    private TextView phone;
    private Requests request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.musician_request_detail_activity);

        db = new DataBaseHelper();
        request = (Requests) getIntent().getSerializableExtra("request");
        user = db.getUser(request.getIdBand());

        googleMapView = (MapView) findViewById(R.id.mapViewDetail);
        address = (TextView) findViewById(R.id.emailRequestDetailTxt);
        email = (TextView) findViewById(R.id.addressRequestDetailTxt);
        phone = (TextView) findViewById(R.id.phoneRequestDetail);

        googleMapView.setVisibility(View.VISIBLE);
        googleMapView.onCreate(null);

        if(user == null){
            Toast.makeText(MusicianRequestDetailActivity.this, "Ocurrió un error interno. Intentá nuevamente", Toast.LENGTH_LONG).show();
            address.setText(" - ");
            email.setText(" - ");
            phone.setText(" - ");
            googleMapView.setVisibility(View.INVISIBLE);
        }
        else {
            address.setText(user.getAddress() + ", " + user.getDistrict());
            email.setText(user.getUserName());
            phone.setText(user.getPhone());
        }

        googleMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                LatLng coordinates = new LatLng(Float.parseFloat(user.getLatitude()), Float.parseFloat(user.getLongitude()));
                googleMap.addMarker(new MarkerOptions().position(coordinates).title("Tu Nueva Banda :)"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Uri gmmIntentUri = Uri.parse("geo:" + user.getLatitude() + "," + user.getLongitude());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        googleMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume(){
        super.onResume();
        googleMapView.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        googleMapView.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        googleMapView.onDestroy();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        googleMapView.onLowMemory();
    }
}
