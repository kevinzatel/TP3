package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MusicianRequests extends AppCompatActivity   {

    DataBaseHelper db;
    TextView requestsEmtpy;
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
        ArrayList<Requests> reqList =  db.getRequestMusician(idmusician);

        if(reqList == null || reqList.size() == 0) {
            lvRequests.setVisibility(View.INVISIBLE);
            requestsEmtpy.setVisibility(View.VISIBLE);

        } else {
            MusicianRequestAdapter reqAdapter = new MusicianRequestAdapter(MusicianRequests.this, reqList);
            lvRequests.setAdapter(reqAdapter);
            reqAdapter.notifyDataSetChanged();
        }
    }

}
