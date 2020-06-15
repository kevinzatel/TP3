package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MusicianRequests extends AppCompatActivity {

    DataBaseHelper db;
    public CollectionReference users;
    ListView lvRequests;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_requests);

        lvRequests = findViewById(R.id.listRequests);

        db = new DataBaseHelper();
        final User user = (User) getIntent().getSerializableExtra("user");
        listRequests();

    }


    public void  listRequests(  ){
        List <String> listRequest = new ArrayList<>();
        Task<QuerySnapshot> task = users.get();


        while(!task.isComplete()){}

        if(task.isSuccessful()) {
            listRequest.clear();
            QuerySnapshot queryDocumentSnapshots = task.getResult();
            List<DocumentSnapshot> reqList = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot r : queryDocumentSnapshots) {
                listRequest.add(r.getString("userName"));

            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listRequest);
            adapter.notify();
            lvRequests.setAdapter(adapter);
        }


/*
        final List<String> listRequest = new ArrayList<>();
        users.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("Error","Error:"+e.getMessage());
                }
                else {
                    listRequest.clear();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        listRequest.add(snapshot.getString("userName"));

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listRequest);
                    adapter.notify();
                    lvRequests.setAdapter(adapter);
                }
            }



        });
*/
    }


}
