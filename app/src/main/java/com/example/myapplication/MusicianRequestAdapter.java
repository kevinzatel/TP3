package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class MusicianRequestAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Requests> reqList;
    private Context mContext;

    public MusicianRequestAdapter(Context context, ArrayList<Requests> objects) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view==null) {
            LayoutInflater LayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = LayoutInflater.inflate(R.layout.request_row, null);
        }
            Requests req = reqList.get(position);

            TextView idband =  view.findViewById(R.id.idBand);
            idband.setText(req.getIdBand());
            TextView rqDate = (TextView) view.findViewById(R.id.rqDate);
            rqDate.setText(req.getDate());
            Button boton = (Button) view.findViewById(R.id.button);
            boton.setText(req.getState());

            if(req.getState().equals("pendiente")){
               boton.setBackgroundResource(R.drawable.custom_button_pending);
               } else if (req.getState().equals("aceptada")){
                boton.setBackgroundResource(R.drawable.custom_button_ok);
                } else {
                boton.setBackgroundResource(R.drawable.custom_button_reject);
            }

            return view;

    }
}
