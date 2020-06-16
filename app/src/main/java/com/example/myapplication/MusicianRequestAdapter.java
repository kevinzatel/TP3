package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class MusicianRequestAdapter extends ArrayAdapter<Requests> {

    private ArrayList<Requests> reqList;
    private Context mContext;
    private  int resourceLayout;

    public MusicianRequestAdapter(@NonNull Context context, int resource, ArrayList<Requests> objects) {
        super(context, resource, objects);
        this.reqList = objects;
        this.mContext = context;
        this.resourceLayout = resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view==null) {
            LayoutInflater LayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = LayoutInflater.from(mContext).inflate(R.layout.request_row, null);
        }
            Requests req = reqList.get(position);

            TextView idband =  view.findViewById(R.id.idBand);
            idband.setText(req.getIdBand());

           //TextView state = (TextView) view.findViewById(R.id.State);
            //state.setText(req.getState());

            TextView rqDate = (TextView) view.findViewById(R.id.rqDate);
            rqDate.setText(req.getDate());

            Button boton = (Button) view.findViewById(R.id.button);


            boton.setText(req.getState());
            if(req.getState().equals("pendiente")){
               boton.setBackgroundResource(R.drawable.custom_button);
               } else if (req.getState().equals("aceptada")){
                boton.setBackgroundResource(R.drawable.custom_button_ok);

                } else {
                boton.setBackgroundResource(R.drawable.custom_button_null);
            }


            return view;



    }
}