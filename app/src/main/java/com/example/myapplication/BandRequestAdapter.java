package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class BandRequestAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Requests> reqList;
    private Context mContext;
    private  int resourceLayout;
    DataBaseHelper db;
    User user;

    public BandRequestAdapter(@NonNull Context context,  ArrayList<Requests> objects) {


        this.reqList = objects;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return reqList.size();
    }

    @Override
    public Object getItem(int position) {
        return reqList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position,  View convertView,  ViewGroup parent) {

        View view = convertView;

        if(view==null) {
            LayoutInflater LayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = LayoutInflater.inflate(R.layout.band_request_row, null);
        }

            final Requests req = reqList.get(position);
            TextView idband =  view.findViewById(R.id.idBand);
            idband.setText(req.getIdMusician());

            TextView rqDate = (TextView) view.findViewById(R.id.rqDate);
            rqDate.setText(req.getDate());

            Spinner status = view.findViewById(R.id.spRequest);
            String [] statusItems = mContext.getResources().getStringArray(R.array.status);
            ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_layout, statusItems);

            status.setAdapter(statusAdapter);
            status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    req.setState(((String) parent.getItemAtPosition(position)).toLowerCase());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            return view;

    }
}
