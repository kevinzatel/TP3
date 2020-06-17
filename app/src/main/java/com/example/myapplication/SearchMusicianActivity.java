package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchMusicianActivity extends AppCompatActivity {
    DataBaseHelper db;
    CheckBox guitarraBox, pianoBox, bateriaBox, vozBox;
    Button activateSearchBtn;
    String[] musicianTypes;
    ArrayList<String> musicianSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_musician);

        db = new DataBaseHelper();
        final User user = (User) getIntent().getSerializableExtra("user");

        guitarraBox = (CheckBox) findViewById(R.id.guitarraBox);
        pianoBox = (CheckBox) findViewById(R.id.pianoBox);
        bateriaBox = (CheckBox) findViewById(R.id.bateriaBox);
        vozBox = (CheckBox) findViewById(R.id.vozBox);
        activateSearchBtn = (Button) findViewById(R.id.activateSearchBtn);
        musicianTypes = getResources().getStringArray(R.array.instrumentos);
        musicianSearch = user.getMusicianSearching();

        if(musicianSearch != null){
            if(musicianSearch.contains(musicianTypes[0])) guitarraBox.setChecked(true);
            if(musicianSearch.contains(musicianTypes[1])) pianoBox.setChecked(true);
            if(musicianSearch.contains(musicianTypes[2])) bateriaBox.setChecked(true);
            if(musicianSearch.contains(musicianTypes[3])) vozBox.setChecked(true);
        }

        activateSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selectedMusicians = new ArrayList<String>();
                if(guitarraBox.isChecked()) selectedMusicians.add(musicianTypes[0]);
                if(pianoBox.isChecked()) selectedMusicians.add(musicianTypes[1]);
                if(bateriaBox.isChecked()) selectedMusicians.add(musicianTypes[2]);
                if(vozBox.isChecked()) selectedMusicians.add(musicianTypes[3]);
                if(selectedMusicians.size() == 0) {
                    Toast.makeText(SearchMusicianActivity.this, "Por favor seleccione al menos un instrumento para activar la búsqueda.", Toast.LENGTH_LONG).show();
                }
                else{
                        try {
                            db.activateMusicianSearch(user, selectedMusicians);
                            Toast.makeText(SearchMusicianActivity.this, "Búsqueda activada.", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e){
                            Toast.makeText(SearchMusicianActivity.this, "Error en conexión con base de datos.", Toast.LENGTH_LONG).show();
                        }
                        finally {
                            User updatedUser = db.getUser(user.getUserName());
                            Intent landingIntent = new Intent(getApplicationContext(), BandLandingPageActivity.class);
                            landingIntent.putExtra("user", updatedUser);
                            startActivity(landingIntent);
                        }
                    }
            }
        });
    }
}
