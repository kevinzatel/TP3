package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;

public class BandRequestsActivity extends AppCompatActivity   {

    DataBaseHelper db;
    TextView requestsEmtpy;
    public CollectionReference users;
    ListView lvRequests;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_requests);

        lvRequests = (ListView) findViewById(R.id.listRequests);
        requestsEmtpy = findViewById(R.id.solicitudesEmpty);

        db = new DataBaseHelper();
        final User user = (User) getIntent().getSerializableExtra("user");
        String idband = user.getUserName();

        ArrayList<Requests> reqList =  db.getRequestBand(idband);




        if(reqList == null || reqList.size()== 0) {
            lvRequests.setVisibility(View.INVISIBLE);
            requestsEmtpy.setVisibility(View.VISIBLE);

        } else {
            BandRequestAdapter  reqAdapter = new BandRequestAdapter(BandRequestsActivity.this, reqList);
            lvRequests.setAdapter(reqAdapter);
            reqAdapter.notifyDataSetChanged();
        }

     /*   lvRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent requestDetailIntent = new Intent(view.getContext(),MusicianRequestDetailActivity.class);
                requestDetailIntent.putExtra("request",)

            }
        });*/



            }



}
