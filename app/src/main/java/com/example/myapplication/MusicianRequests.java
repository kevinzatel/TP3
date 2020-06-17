package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

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
            MusicianRequestAdapter reqAdapter = new MusicianRequestAdapter(MusicianRequests.this, R.layout.request_row, reqList);
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
