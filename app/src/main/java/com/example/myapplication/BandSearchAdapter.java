package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BandSearchAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<User> list;
    private Context context;

    public BandSearchAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.band_search_list, null);
        }

        TextView bandDesc = (TextView) view.findViewById(R.id.bandDesc);
        bandDesc.setText(list.get(position).getNickname());

        ImageView btnDetail = (ImageView) view.findViewById(R.id.iconDetail);

        btnDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hiciste click en " + list.get(position).getNickname(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}

