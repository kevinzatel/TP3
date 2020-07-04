package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;

public class BandRequestsActivity extends AppCompatActivity   {

    DataBaseHelper db;
    TextView requestsEmtpy;
    ListView lvRequests;
    Button saveChangesBtn;
    BandRequestAdapter reqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_requests);

        lvRequests = (ListView) findViewById(R.id.listRequests);
        requestsEmtpy = findViewById(R.id.solicitudesEmpty);
        saveChangesBtn = findViewById(R.id.saveChangesRequest);

        db = new DataBaseHelper();
        final User user = (User) getIntent().getSerializableExtra("user");
        String idband = user.getUserName();

        ArrayList<Requests> reqList =  db.getRequestBand(idband);

        if(reqList == null || reqList.size()== 0) {
            lvRequests.setVisibility(View.INVISIBLE);
            requestsEmtpy.setVisibility(View.VISIBLE);
            saveChangesBtn.setVisibility(View.INVISIBLE);

        } else {
            reqAdapter = new BandRequestAdapter(BandRequestsActivity.this, reqList);
            lvRequests.setAdapter(reqAdapter);
            saveChangesBtn.setVisibility(View.VISIBLE);
        }

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean flag_1 = false;
                Boolean flag_2 = false;

                for (int i = 0; i < reqAdapter.getCount(); i++){
                    Requests item = (Requests) reqAdapter.getItem(i);
                    if(!(((String) item.getState()).toLowerCase().equals("pendiente"))){
                        flag_1 = db.updateRequestStatus(((String) item.getIdBand()), ((String) item.getIdMusician()), ((String) item.getState()).toLowerCase());
                        flag_2 = true;
                    }
                }

                if (flag_1 == true){
                    Intent landingIntent = new Intent(getApplicationContext(), BandLandingPageActivity.class);
                    landingIntent.putExtra("user", user);
                    Toast.makeText(BandRequestsActivity.this, "Solicitud Respondida", Toast.LENGTH_LONG).show();
                    startActivity(landingIntent);
                }
                else {
                    if(flag_2 == true){
                        reqAdapter.notifyDataSetChanged();
                        Toast.makeText(BandRequestsActivity.this, "Ocurrió un error interno. Intentá nuevamente", Toast.LENGTH_LONG).show();
                    } else {
                        reqAdapter.notifyDataSetChanged();
                        Toast.makeText(BandRequestsActivity.this, "Tenés que Aceptar o Rechazar alguna Solicitud", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
