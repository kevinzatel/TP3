package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.CollectionReference;
import java.util.ArrayList;

public class MusicianRequests extends AppCompatActivity   {

    DataBaseHelper db;
    TextView requestsEmtpy;
    public CollectionReference users;
    ListView lvRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_requests);

        lvRequests = (ListView) findViewById(R.id.listRequests);
        requestsEmtpy = findViewById(R.id.solicitudesEmpty);

        db = new DataBaseHelper();
        final User user = (User) getIntent().getSerializableExtra("user");
        String idmusician = user.getUserName();
        ArrayList<Requests> reqList =  db.getRequest(idmusician);

        if(reqList == null || reqList.size()== 0) {
            lvRequests.setVisibility(View.INVISIBLE);
            requestsEmtpy.setVisibility(View.VISIBLE);

        } else {
            MusicianRequestAdapter reqAdapter = new MusicianRequestAdapter(MusicianRequests.this, reqList);
            lvRequests.setAdapter(reqAdapter);
            reqAdapter.notifyDataSetChanged();
        }

        lvRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Requests obtainedRequest = (Requests) parent.getItemAtPosition(position);
                if(obtainedRequest != null && obtainedRequest.getState().equals("aceptada")) {
                    Intent requestDetailIntent = new Intent(view.getContext(), MusicianRequestDetailActivity.class);
                    requestDetailIntent.putExtra("request", obtainedRequest);
                    startActivity(requestDetailIntent);
                }
            }
        });
    }

}
