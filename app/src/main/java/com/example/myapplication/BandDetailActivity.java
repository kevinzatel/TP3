package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BandDetailActivity extends AppCompatActivity {

    private DataBaseHelper db;
    private User userLogged;
    private User userBand;
    private Button requestBtn;
    private TextView bandNameTxt;
    private TextView timeOfDayTxt;
    private TextView districtTxt;
    private ProgressBar progressBar;
    private ImageView requestSuccess;
    private ArrayList<User> externalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_detail);
        db = new DataBaseHelper();
        externalData = new ArrayList<>();

        externalData = (ArrayList<User>) getIntent().getSerializableExtra("data");
        userLogged = (User) externalData.get(0);
        userBand = (User) externalData.get(1);
        bandNameTxt = (TextView) findViewById(R.id.bandDetailNameTxt);
        timeOfDayTxt = (TextView) findViewById(R.id.bandDetailDayTime);
        districtTxt = (TextView) findViewById(R.id.bandDetailDistrictTxt);
        requestBtn = (Button) findViewById(R.id.sendRequestBtn);
        progressBar = (ProgressBar) findViewById(R.id.bandDetailProgressBar);
        requestSuccess = (ImageView) findViewById(R.id.requestSuccesImg);

        bandNameTxt.setText(userBand.getNickname());
        timeOfDayTxt.setText(userBand.getTimeOfDay());
        districtTxt.setText(userBand.getDistrict());

        progressBar.setVisibility(View.INVISIBLE);
        requestSuccess.setVisibility(View.INVISIBLE);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MakeRequest makeRequestAsyncTask = new MakeRequest();
                String [] params = { userBand.getUserName(), userLogged.getUserName() };
                makeRequestAsyncTask.execute(params);

            }
        });

    }

    private class MakeRequest extends AsyncTask<String, Void, Boolean> {

        private String idBand;
        private String idMusician;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            requestBtn.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {

            this.idBand = params[0];
            this.idMusician = params[1];

            Boolean result = db.insertRequest(this.idBand, this.idMusician);

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if(result){
                progressBar.setVisibility(View.INVISIBLE);
                requestBtn.setVisibility(View.INVISIBLE);
                requestSuccess.setVisibility(View.VISIBLE);
                Toast.makeText(BandDetailActivity.this, "Solicitud Enviada", Toast.LENGTH_LONG).show();
            }
            else {
                requestBtn.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                requestBtn.setVisibility(View.VISIBLE);
                requestSuccess.setVisibility(View.INVISIBLE);
                Toast.makeText(BandDetailActivity.this, "Ocurrió un error interno. Intentá nuevamente", Toast.LENGTH_LONG).show();
            }
        }
    }
}
